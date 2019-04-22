package com.brunodles.concretetestes

import android.app.Activity
import android.app.Instrumentation
import android.app.Instrumentation.*
import android.content.Intent
import android.os.Bundle
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.Intents.intending
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Rule
    @JvmField
    val rule = IntentsTestRule<MainActivity>(MainActivity::class.java, true, true)

    @Test
    fun whenOpen_shouldStartWithEmptyUsername_andPassword() {
        onView(withId(R.id.username)).check(matches(isDisplayed()))
        onView(withId(R.id.username)).check(matches(withText("")))
        onView(withId(R.id.password)).check(matches(isDisplayed()))
        onView(withId(R.id.password)).check(matches(withText("")))
    }

    @Test
    fun whenLogin_withoutUsername_shouldShowEmptyUsernameMessage() {
        onView(withId(R.id.password)).perform(ViewActions.typeText("admin"))

        onView(withText(R.string.login_button)).perform(click())

        onView(withText("Empty Username")).check(matches(isDisplayed()))
    }

    @Test
    fun whenLogin_withoutPassword_shouldShowEmptyPasswordMessage() {
        onView(withId(R.id.username)).perform(ViewActions.typeText("admin"))

        onView(withId(R.id.login)).perform(click())

        onView(withText("Empty Password")).check(matches(isDisplayed()))
    }

    @Test
    fun whenLogin_withValidUsername_andPassword_shouldCallHomeActivity() {
        Intents.init()
        val homeActivityIntentMatcher = IntentMatchers.hasComponent(HomeActivity::class.java.name)
        intending(homeActivityIntentMatcher)
            .respondWith(ActivityResult(Activity.RESULT_CANCELED, null))

        onView(withId(R.id.username)).perform(typeText("brunodles"))
        onView(withId(R.id.password)).perform(typeText("Ab.345678"))

        onView(withId(R.id.login)).perform(click())

        intended(homeActivityIntentMatcher)

        Intents.release()
    }

    @Test
    fun whenLogin_withInvalidPassword_shouldShowInvalidLoginOrPasswordMessage() {
        onView(withId(R.id.username)).perform(typeText("brunodles"))
        onView(withId(R.id.password)).perform(typeText("1234567"))

        onView(withId(R.id.login)).perform(click())

        onView(withText("Invalid Username or Password")).check(matches(isDisplayed()))
    }

}
