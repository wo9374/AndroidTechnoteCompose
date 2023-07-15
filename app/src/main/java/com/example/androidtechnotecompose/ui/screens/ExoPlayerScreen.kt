package com.example.androidtechnotecompose.ui.screens

import android.view.ViewGroup
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.androidtechnotecompose.R
import com.example.androidtechnotecompose.extensions.noRippleClickable
import com.example.androidtechnotecompose.extensions.setLandscape
import com.example.androidtechnotecompose.extensions.setPortrait
import com.example.androidtechnotecompose.ui.theme.Purple200
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
fun ExoPlayerScreen() {
    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(
                MediaItem.fromUri("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
            )
            prepare()
            playWhenReady = true
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        var isPlaying by remember { mutableStateOf(true) }

        var totalDuration by remember { mutableStateOf(0L) }
        var currentTime by remember { mutableStateOf(0L) }
        var bufferedPercentage by remember { mutableStateOf(0) }

        var isFullScreen by remember { mutableStateOf(false) }

        DisposableEffect(key1 = Unit) {
            //Bottom Control 에서 사용할 시간과 %를 알기 위한 Listener
            val listener = object : Player.Listener {
                override fun onEvents(player: Player, events: Player.Events) {
                    super.onEvents(player, events)
                    totalDuration = player.duration.coerceAtLeast(0L)
                    currentTime = player.currentPosition.coerceAtLeast(0L)
                    bufferedPercentage = player.bufferedPercentage
                }
            }
            exoPlayer.addListener(listener)

            onDispose {
                exoPlayer.removeListener(listener)
                exoPlayer.release()
            }
        }


        //PlayerControls onOff 판단할 Boolean
        var shouldShowControls by remember { mutableStateOf(true) }

        AndroidView(
            modifier = Modifier.noRippleClickable {
                shouldShowControls =
                    shouldShowControls.not() //VideoView 를 클릭할 때 PlayerControls OnOFf
            },
            factory = {
                StyledPlayerView(context).apply {
                    player = exoPlayer
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                    )
                    useController = false //Default Player Control Disable
                }
            }
        )

        //PlayerControls 가 표시 visible 일때 시간 만큼 invisible 자동 변환
        LaunchedEffect(key1 = shouldShowControls) {
            if (shouldShowControls) {
                delay(3000)
                shouldShowControls = false
            }
        }


        PlayerControls(
            modifier = Modifier.fillMaxSize(),
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

    //다음영상 추가
    //val secondItem = MediaItem.fromUri("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4")
    //exoPlayer.addMediaItem(secondItem)
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlayerControls(
    modifier: Modifier = Modifier,

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
        Box(modifier = Modifier.background(Color.Black.copy(alpha = 0.6f))) {

            CenterControlsPanel(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                isPlaying = isPlaying,
                onReplayClick = onReplayClick,
                onPauseToggle = onPauseToggle,
                onForwardClick = onForwardClick,
            )

            BottomControlsPanel(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
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
        horizontalArrangement = Arrangement.SpaceEvenly
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

    Column(modifier = modifier.padding(bottom = 10.dp)) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // buffer bar
            Slider(
                value = buffer.toFloat(),
                enabled = false,
                onValueChange = { /*do nothing*/ },
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
                text = totalTime.formatMinSec(),
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

fun Long.formatMinSec(): String {
    return if (this == 0L) {
        "..."
    } else {
        String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(this),
            TimeUnit.MILLISECONDS.toSeconds(this) -
                    TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(this)
                    )
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CenterControlsPreview() {
    val isVideoPlaying = false

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {

        //이전보기
        IconButton(modifier = Modifier.size(40.dp), onClick = {}) {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.ic_replay_5_24),
                contentDescription = "Replay 5 seconds"
            )
        }

        // 재생/일시정지 토글
        IconButton(modifier = Modifier.size(40.dp), onClick = {}) {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                painter = if (isVideoPlaying) {
                    painterResource(id = R.drawable.ic_pause_24)
                } else {
                    painterResource(id = R.drawable.ic_play_arrow_24)
                },
                contentDescription = "Play/Pause"
            )
        }

        //건너뛰기
        IconButton(modifier = Modifier.size(40.dp), onClick = {}) {
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
@Preview(showBackground = true)
fun BottomControlsPreview() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // buffer bar
            Slider(
                value = 0f,
                enabled = false,
                onValueChange = { /*do nothing*/ },
                valueRange = 0f..100f,
                colors = SliderDefaults.colors(
                    disabledThumbColor = Color.Transparent,
                    disabledActiveTrackColor = Color.Gray
                )
            )

            // seek bar
            Slider(
                value = 1f,
                onValueChange = {},
                valueRange = 0f..30f,
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
                text = "00:00",
                color = Purple200
            )

            IconButton(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterVertically),
                onClick = {}
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
