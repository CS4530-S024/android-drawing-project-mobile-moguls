package com.example.drawingapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class EspressoTests {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testSplashToDrawScreenButton() {
        onView(withText("Start Drawing")).perform(click())
        onView(withId(R.id.circleShapeButton)).check(matches(isDisplayed()))}

    @Test
    fun testUserChangingPenButtons() {
        onView(withText("Start Drawing")).perform(click())
        onView(withText("Small")).perform(click())
        onView(withText("Oval")).perform(click())
        onView(withId(R.id.view)).perform(click())
    }

    @Test
    fun testUserGoingToAllScreens(){
        onView(withText("Start Drawing")).perform(click())
        onView(withText("Main Screen")).perform(click())
        onView(withText("Go to Save Screen")).perform(click())
    }
}