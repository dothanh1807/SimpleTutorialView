# SimpleTutorialView
<img width="350" alt="Screenshot 2020-12-13 at 16 02 45" src="https://user-images.githubusercontent.com/52622713/102007558-bb160480-3d5c-11eb-8fc9-5799a5462d51.png"> ![ezgif com-gif-maker](https://user-images.githubusercontent.com/52622713/102007560-bfdab880-3d5c-11eb-98f5-237e9064d74f.gif)

Add it in your gradle project:
```javascript
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Add the dependency:
```javascript
dependencies {
	implementation 'com.github.dothanh1807:SimpleTutorialView:1.0.0'
}
```
Code:

```javascript
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <TextView
        android:id="@+id/textViewX"
        android:text="Hello World!"
        android:layout_centerInParent="true"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>

    <Button
        android:id="@+id/buttonX"
        android:text="Button"
        android:layout_alignParentBottom="true"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>

    <Button
        android:id="@+id/iconX"
        android:text="Icon"
        android:layout_alignParentEnd="true"
        android:layout_marginTop="50dp"
        android:layout_width="wrap_content"
        android:layout_height="40dp"/>

    <com.vllenin.tutorialview.HighLightTutorialView
        android:id="@+id/tutorialView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
</RelativeLayout>
```

```javascript
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        window?.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val mapViews = mutableMapOf<String, View>()
        val textForIcon = "This is icon"
        mapViews[textForIcon] = iconX
        val textForTexView = "This is textView"
        mapViews[textForTexView] = textViewX
        val textButtonX = "This is button"
        mapViews[textButtonX] = buttonX

        Handler(Looper.getMainLooper()).postDelayed({
            tutorialView.focusToView(mapViews)
                .setCancelListener {
                    // Marked as read to SharedPreferences file
//                    val mapViewsSecond = mutableMapOf<String, View>()
//                    val textForIcon2 = "This is icon 2"
//                    mapViews[textForIcon] = iconX
//                    tutorialView.focusToView(mapViews)
//                        .setCancelListener {
//                            // Marked as read to SharedPreferences file
//                        }
                }
        }, 2000)

    }

}
```
