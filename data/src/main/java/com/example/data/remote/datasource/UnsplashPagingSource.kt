package com.example.data.remote.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.BuildConfig
import com.example.data.model.toEntity
import com.example.domain.entity.UnsplashEntity
import retrofit2.HttpException
import java.io.IOException

const val STARTING_KEY = 1

//Paging 참고 https://medium.com/@mohammadjoumani/paging-with-clean-architecture-in-jetpack-compose-775fbf589256
class UnsplashPagingSource(
    private val remoteDataSource: RemoteDataSource
) : PagingSource<Int, UnsplashEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashEntity> {
        return try {
            val currentPage = params.key ?: STARTING_KEY
            val pictures = remoteDataSource.getUnsplashPhoto(
                apiKey = BuildConfig.UNSPLASH_ACCESS_KEY,
                pageNumber = currentPage
            ).map {
                it.toEntity()
            }

            LoadResult.Page(
                data = pictures,
                prevKey = if (currentPage == STARTING_KEY) null else currentPage - 1,
                nextKey = if (pictures.isEmpty()) null else currentPage + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}