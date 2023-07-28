package com.example.androidtechnotecompose.ui.screens

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.example.androidtechnotecompose.R
import com.example.androidtechnotecompose.extensions.formatMinSec
import com.example.androidtechnotecompose.extensions.noRippleClickable
import com.example.androidtechnotecompose.extensions.setLandscape
import com.example.androidtechnotecompose.extensions.setPortrait
import com.example.androidtechnotecompose.ui.theme.Purple200

@Composable
fun ExoPlayerScreen() {
    val videoUrl =
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        VideoPlayer(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 10f),
            uri = videoUrl
        )
    }
}

@Composable
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun VideoPlayer(
    modifier: Modifier,
    uri: String
) {
    val context = LocalContext.current

    //재생 여부 판단할 Boolean
    var isPlaying by remember { mutableStateOf(true) }

    //PlayerControls onOff 판단할 Boolean
    var shouldShowControls by remember { mutableStateOf(true) }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val defaultDataSourceFactory = DefaultDataSource.Factory(context)

            val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(
                context, defaultDataSourceFactory
            )
            val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(uri))

            setMediaSource(source)
            prepare()

            playWhenReady = false //getPlaybackState() == STATE_READY 일 때 재생 진행할지 여부 설정
            //ViewLifecycleOwner 로 인한 Resume 자동재생 구현으로 false 지정

            videoScalingMode = C.VIDEO_SCALING_MODE_DEFAULT
            //크기 조정 모드를 설정하면 MediaCodec 기반 비디오 렌더러가 활성화되고 출력 표면이 SurfaceView에 의해 소유되는 경우에만 적용

            repeatMode = Player.REPEAT_MODE_ONE
            //비디오 반복 설정
        }
    }

    //Bottom Control 에서 사용할 시간과 %를 알기 위한 Listener
    var totalDuration by remember { mutableStateOf(0L) }
    var currentTime by remember { mutableStateOf(0L) }
    var bufferedPercentage by remember { mutableStateOf(0) }

    val listener = object : Player.Listener {
        override fun onEvents(player: Player, events: Player.Events) {
            super.onEvents(player, events)
            totalDuration = player.duration.coerceAtLeast(0L)
            currentTime = player.currentPosition.coerceAtLeast(0L)
            bufferedPercentage = player.bufferedPercentage
        }
    }
    exoPlayer.addListener(listener)


    //Background 일때 재생 정지를 위한 Lifecycle, Observer
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)
    val lifecycle = lifecycleOwner.value.lifecycle

    val observer = LifecycleEventObserver { owner, event ->
        when (event) {
            Lifecycle.Event.ON_PAUSE -> {
                exoPlayer.pause()
            }

            Lifecycle.Event.ON_RESUME -> {
                if (isPlaying)
                    exoPlayer.play() //Controller 재생 상태가 play 상태일 때만 재생
            }

            else -> {}
        }
    }
    lifecycle.addObserver(observer)

    Box(modifier = modifier) {
        DisposableEffect(
            AndroidView(
                modifier = Modifier
                    .matchParentSize()
                    .noRippleClickable {
                        //VideoView 를 클릭할 때 PlayerControls OnOff
                        shouldShowControls = shouldShowControls.not()
                    },
                factory = {
                    PlayerView(context).apply {

                        //Default Player Control Disable
                        hideController()
                        useController = false
                        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM

                        player = exoPlayer
                        layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                    }
                }
            )
        ) {
            onDispose {
                exoPlayer.removeListener(listener)
                exoPlayer.release()
                lifecycle.removeObserver(observer)
            }
        }

        //PlayerControls 가 visible 일때 시간 만큼 invisible 자동 변환
        /*LaunchedEffect(key1 = shouldShowControls) {
            if (shouldShowControls) {
                delay(3000)
                shouldShowControls = false
            }
        }*/

        var isFullScreen by remember { mutableStateOf(false) }

        PlayerControls(
            modifier = Modifier.matchParentSize(),
            isVisible = { shouldShowControls },

            isPlaying = { isPlaying },
            onReplayClick = { exoPlayer.seekBack() },
            onForwardClick = { exoPlayer.seekForward() },
            onPauseToggle = {
                if (exoPlayer.isPlaying) {
                    exoPlayer.pause()
                } else {
                    exoPlayer.play()
                }
                isPlaying = isPlaying.not()
            },

            totalDuration = { totalDuration },
            currentTime = { currentTime },
            bufferPercentage = { bufferedPercentage },
            onSeekChanged = { timeMs: Float -> exoPlayer.seekTo(timeMs.toLong()) },

            fullScreenToggle = {
                if (isFullScreen.not()) {
                    context.setLandscape()
                } else {
                    context.setPortrait()
                }
                isFullScreen = isFullScreen.not()
            }
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlayerControls(
    modifier: Modifier,

    isVisible: () -> Boolean,

    isPlaying: () -> Boolean,
    onReplayClick: () -> Unit,
    onForwardClick: () -> Unit,
    onPauseToggle: () -> Unit,

    totalDuration: () -> Long,
    currentTime: () -> Long,
    bufferPercentage: () -> Int,
    onSeekChanged: (timeMs: Float) -> Unit,
    fullScreenToggle: () -> Unit
) {
    val visible = remember(isVisible()) { isVisible() }

    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
        ) {

            CenterControlsPanel(
                modifier = Modifier.matchParentSize(),
                isPlaying = isPlaying,
                onReplayClick = onReplayClick,
                onPauseToggle = onPauseToggle,
                onForwardClick = onForwardClick,
            )

            BottomControlsPanel(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .animateEnterExit(
                        enter = slideInVertically(
                            initialOffsetY = { fullHeight: Int ->
                                fullHeight
                            }
                        ),
                        exit = slideOutVertically(
                            targetOffsetY = { fullHeight: Int ->
                                fullHeight
                            }
                        )
                    ),
                totalDuration = totalDuration,
                currentTime = currentTime,
                bufferPercentage = bufferPercentage,
                onSeekChanged = onSeekChanged,
                fullScreenToggle = fullScreenToggle
            )
        }
    }
}

@Composable
fun CenterControlsPanel(
    modifier: Modifier,
    isPlaying: () -> Boolean,
    onReplayClick: () -> Unit,
    onPauseToggle: () -> Unit,
    onForwardClick: () -> Unit,
) {
    val isVideoPlaying = remember(isPlaying()) {
        isPlaying()
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        //이전보기
        IconButton(modifier = Modifier.size(40.dp), onClick = onReplayClick) {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.ic_replay_5_24),
                contentDescription = "Replay 5 seconds"
            )
        }

        // 재생/일시정지 토글
        IconButton(modifier = Modifier.size(40.dp), onClick = onPauseToggle) {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                painter =
                if (isVideoPlaying) {
                    painterResource(id = R.drawable.ic_pause_24)
                } else {
                    painterResource(id = R.drawable.ic_play_arrow_24)
                },
                contentDescription = "Play/Pause"
            )
        }

        //건너뛰기
        IconButton(modifier = Modifier.size(40.dp), onClick = onForwardClick) {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.ic_forward_5_24),
                contentDescription = "Forward 10 seconds"
            )
        }
    }
}

@Composable
fun BottomControlsPanel(
    modifier: Modifier,
    totalDuration: () -> Long,
    currentTime: () -> Long,
    bufferPercentage: () -> Int,
    onSeekChanged: (timeMs: Float) -> Unit,
    fullScreenToggle: () -> Unit
) {
    val totalTime = remember(totalDuration()) { totalDuration() }
    val videoTime = remember(currentTime()) { currentTime() }
    val buffer = remember(bufferPercentage()) { bufferPercentage() }

    Column(modifier = modifier) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // buffer bar
            Slider(
                value = buffer.toFloat(),
                enabled = false,
                onValueChange = { },
                valueRange = 0f..100f,
                colors = SliderDefaults.colors(
                    disabledThumbColor = Color.Transparent,
                    disabledActiveTrackColor = Color.Gray
                )
            )

            // seek bar
            Slider(
                modifier = Modifier.fillMaxWidth(),
                value = videoTime.toFloat(),
                onValueChange = onSeekChanged,
                valueRange = 0f..totalTime.toFloat(),
                colors =
                SliderDefaults.colors(
                    thumbColor = Purple200,
                    activeTickColor = Purple200
                )
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterVertically),
                text = videoTime.formatMinSec(),
                color = Purple200
            )

            IconButton(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterVertically),
                onClick = fullScreenToggle
            ) {
                Image(
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.ic_fullscreen_24),
                    contentDescription = "Enter/Exit fullscreen"
                )
            }
        }
    }
}