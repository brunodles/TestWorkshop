package com.brunodles.concretetestes

import android.app.Activity
import android.app.Instrumentation.*
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

import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Rule
    @JvmField
    val rule = IntentsTestRule<MainActivity>(MainActivity::class.java, true, true)

    @Test
    fun whenOpen_shouldStartWithEmptyUsername_andPassword() {
        loginAssert {
            isUsernameEmpty()
            isPasswordEmpty()
        }
    }

    @Test
    fun whenLogin_withoutUsername_shouldShowEmptyUsernameMessage() {
        loginRobot {
            password("admin")
            login()
        }

        loginAssert {
            isEmptyUsernameMessageDisplayed()
        }
    }

    @Test
    fun whenLogin_withoutPassword_shouldShowEmptyPasswordMessage() {
        loginRobot {
            username("admin")
            login()
        }

        loginAssert {
            isEmptyPasswordMessageDisplayed()
        }
    }

    @Test
    fun whenLogin_withInvalidPassword_shouldShowInvalidLoginOrPasswordMessage() {
        loginRobot {
            username("brunodles")
            password("1234567")
            login()
        }
        loginAssert {
            isInvalidUsernameOrPasswordMessageDispplayed()
        }
    }

    @Test
    fun whenLogin_withValidUsername_andPassword_shouldCallHomeActivity() {
        loginArrange {
            mockHomeActivity()
        }

        loginRobot {
            username("brunodles")
            password("Ab.345678")
            login()
        }

        loginAssert {
            homeActivityWasCalled()
        }

    }
}

class loginArrange(block: loginArrange.() -> Unit) {
    fun mockHomeActivity() {
        intending(IntentMatchers.hasComponent(HomeActivity::class.java.name))
            .respondWith(ActivityResult(Activity.RESULT_CANCELED, null))
    }

    init {
        block(this)
    }

}
class loginRobot(block: loginRobot.() -> Unit) {
    fun password(password: String) {
        onView(withId(R.id.password)).perform(ViewActions.typeText(password))
    }

    fun login() {
        onView(withText(R.string.login_button)).perform(click())
    }

    fun username(username: String) {
        onView(withId(R.id.username)).perform(ViewActions.typeText(username))
    }

    init {
        block(this)
    }


}
class loginAssert(block: loginAssert.() ->  Unit) {

    init {
        block.invoke(this)
    }

    fun isUsernameEmpty() = isViewEmpty(R.id.username)

    fun isPasswordEmpty() = isViewEmpty(R.id.password)

    private fun isViewEmpty(view: Int) {
        onView(withId(view)).check(matches(isDisplayed()))
        onView(withId(view)).check(matches(withText("")))
    }

    fun isEmptyUsernameMessageDisplayed() =
        isMessageDisplayed("Empty Username")

    fun isEmptyPasswordMessageDisplayed() =
        isMessageDisplayed("Empty Password")

    fun isInvalidUsernameOrPasswordMessageDispplayed() =
        isMessageDisplayed("Invalid Username or Password")

    private fun isMessageDisplayed(message: String) {
        onView(withText(message)).check(matches(isDisplayed()))
    }

    fun homeActivityWasCalled() {
        intended(IntentMatchers.hasComponent(HomeActivity::class.java.name))
    }

}
