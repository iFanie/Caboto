# Caboto
Annotation based Android navigation

### Intallation
```gradle
implementation 'com.izikode.izilib:caboto:0.1'
kapt 'com.izikode.izilib:caboto-compiler:0.1'
```
Caboto generates <b>Kotlin</b> source classes so you also need to include the kaptKotlin directory in your 
app's source sets. In the Android block of your app's gradle, add the following:
```kotlin
android {
    ...
    
    sourceSets {
        main {
            java {
                srcDir "${buildDir.absolutePath}/generated/source/kaptKotlin/"
            }
        }
    }
}
```
### Usage
- Decorate your Activities and your initialization variables. Provide the course to your current Activity
and optionally give an alias to your initialization variables.
```kotlin
@LandHo(course = "initial/main")
class MainActivity : AppCompatActivity() {

    @CourseToken(alias = "MainActivityTitle")
    lateinit var title: String
}
```
- Build your project.
- Caboto will take you to your destination.
```kotlin
Caboto.NAVIGATOR
        .initial.main                       /* Course */
        .prepare(title = "Caboto rocks")    /* Initialization variables */
        .setSail(this)                      /* Go */
```
#### Notes
- Get the initialization values from the intent extras, either using the variable name, or with the alias,
if you have set one.
```kotlin
intent.extras?.getString("MainActivityTitle")?.let {
            title = it
        }
```
- Initialization variables can be nullable, and thus, the prepare function will be appropriately overloaded.