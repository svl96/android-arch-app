package com.yandex.android.mynotesandroid

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingPolicies
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.text.format.DateUtils
import com.yandex.android.mynotesandroid.ui.MainActivity

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit
import android.support.test.espresso.Espresso
import android.support.test.espresso.IdlingResource
import android.support.test.espresso.action.ViewActions.click


@RunWith(AndroidJUnit4::class)
class EspressoTest {

    @Rule
    @JvmField
    var mActivityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun addButton_AreClickable() {
//        val waitingTime = DateUtils.SECOND_IN_MILLIS * 2
        onView(withId(R.id.fab)).perform(click())
//
//        IdlingPolicies.setMasterPolicyTimeout(
//                waitingTime * 2, TimeUnit.MILLISECONDS)
//        IdlingPolicies.setIdlingResourceTimeout(
//                waitingTime * 2, TimeUnit.MILLISECONDS)
//
//        val idlingResource = ElapsedTime(waitingTime)
//        Espresso.registerIdlingResources(idlingResource)

        onView(withId(R.id.title_label)).check(matches(isDisplayed()))

//        Espresso.unregisterIdlingResources(idlingResource)
    }

}