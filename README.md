Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        compile 'com.github.x930073498:BaseRecyclerItem:1.0.0'
	}
	
	
you shuold enable databinding first


	dataBinding {
	         enabled = true
	}


if databingding is conflicted with kotlin then  you should add
		
	       kapt { generateStubs = true }
		
	
in you app.gradle within tag android  and add a dependencies 

	      kapt 'com.android.databinding:compiler:$your-databingding-version(3.0.0-alpha4)'
		
