package com.example.weather_forcast_app.data


import com.google.common.truth.Truth.assertThat

import kotlinx.coroutines.runBlocking

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.data.db.AppDatabase
import com.example.data.db.FavouriteCityDao
import com.example.data.models.FavouriteCity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class TestAppDb {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var dao: FavouriteCityDao
    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.favouriteCityDao()
    }

    @After
    fun teardown() {
        database.close()
    }
    @Test
    fun insertFavItem() = runBlocking {

        val favItem = FavouriteCity("suez","السويس",30.34,32.29)
        dao.addCityToFavourite(favItem)
        val allFavItems = dao.getAllFavouriteCities()
        assertThat(allFavItems).contains(favItem)

    }
    @Test
    fun deleteFavItem() = runBlocking {
        val favItem = FavouriteCity("ismaillia","اسماعيلية" ,30.59 ,32.27 )
        dao.addCityToFavourite(favItem)
        dao.deleteCityFromFavourite(favItem)
        val allFavItems = dao.getAllFavouriteCities()
        assertThat(allFavItems).doesNotContain(favItem)
    }
}