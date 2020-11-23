package com.example.nytimesapplication

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.nytimesapplication.R
import com.example.nytimesapplication.mostpopularnews.presentation.ui.fragments.MostPopularNewsFragment
import org.hamcrest.Matchers


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
/**
 * This class was designed & implemented to test recycler view using instrumented test
 */
@RunWith(AndroidJUnit4::class)
class AppInstrumentedTest {
    @Test
    fun testFragment() {
        val scenario = launchFragmentInContainer<MostPopularNewsFragment>()
        try {
            Thread.sleep(4000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        // Click the item on position 2 in recycler view
        onView(withId(R.id.rvNews))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    2,
                    click()
                )
            )
        // Go to news details activity and sleep for 3000 ms
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        //go back to main activity
        pressBack()
        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        // Click the item on position 3 in recycler view
        onView(withId(R.id.rvNews))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    3,
                    click()
                )
            )
        // Go to news details activity and sleep for 3000 ms
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        //go back to main activity
        pressBack()

        //refresh the data
        onView(withId(R.id.srlNews))
            .perform(
                withCustomConstraints(
                    swipeDown(),
                    isDisplayingAtLeast(85)
                )
            )
        //sleep for 4000 ms then exit
        try {
            Thread.sleep(4000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }


    fun withCustomConstraints(
        action: ViewAction,
        constraints: Matcher<View>
    ): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return constraints
            }

            override fun getDescription(): String {
                return action.description
            }

            override fun perform(
                uiController: UiController,
                view: View
            ) {
                action.perform(uiController, view)
            }
        }
    }

}