package com.example.customstatusbar.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ContentView(){
    val pagerState = rememberPagerState(pageCount = 4)
    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )
    {
        PagerContent(pagerState =  pagerState)
    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerContent(pagerState: PagerState) {
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()) { page ->
        when(page){
            0 -> Screen1()
            1 -> Screen2()
            2 -> Screen3()
            3 -> Screen4()
        }
    }
}