package com.example.foodorderingapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [LocalRestaurant::class],
    version = 2,
    exportSchema = false)
abstract class RestaurantsDb : RoomDatabase() { /** RestaurantsDb is an abstract class that inherits from RoomDatabase().
                                                This will allow Room to create the actual implementation of the database
                                                behind the scenes and hide all the heavy implementation details from us. */

    abstract val dao : RestaurantsDao
    /** We know that the database class should expose a DAO object so that we can interact
                                           with the database. By leaving it abstract, we allow Room to provide its implementation
                                           behind the scenes. */



    /** Since we want only one memory reference to our database (and not create other database instances in other parts of the app),
     * we made sure that our INSTANCE variable conforms to the singleton pattern. Essentially, the singleton pattern allows us to hold
     * a static reference to an object so that it lives as long as our application does.By following this approach, anytime we need to
     * access the Room database from different parts of the app, we can call the getDaoInstance() method, which returns an instance of
     * RestaurantsDao. Additionally, we can be sure that it's always the same memory reference and that no concurrency issues will occur
     * since we have wrapped the instance creation code inside a synchronized block. */

   /* companion object{  /** Even though we declared a variable to hold our DAO object, we still need to find a way to build the database
                           and obtain a reference to the RestaurantsDao instance that Room will create for us. Inside the RestaurantsDb
                           class, add companion object and then add the buildDatabase method: */

        @Volatile /** This means that writes to this field are immediately made visible to other threads. */
        private var INSTANCE: RestaurantsDao? = null

        fun getDaoInstance(context: Context): RestaurantsDao {
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = buildDatabase(context).dao /** The .dao is an accessor or get to get the DAO object*/
                    INSTANCE = instance
                }
                return instance
            }
        }

        private fun buildDatabase(context:Context): /** This method returns a RestaurantsDb instance. */

                RestaurantsDb = Room.databaseBuilder( /** This is the Room database builder constructor*/

            context.applicationContext, /** A Context object that we provided from the context input argument of our buildDatabase method.*/

            RestaurantsDb::class.java,  /** The class of the database you're trying to build, that is, the RestaurantsDb class. */

            "restaurants_database")  /** A name for the database – we named it "restaurants_database".*/

            .fallbackToDestructiveMigration()  /** The builder returns a RoomDatabase.Builder object on which we called .fallbackToDestructiveMigration().
                                                   This means that, in the case of a schema change (such as performing changes in the entity class and
                                                   modifying the table columns), the tables would be dropped (or deleted) instead of trying to migrate the
                                                   contents from the previous schema (which would have been a bit more complex). */

            .build()  /**build() on the builder object so that our buildDatabase() method returns a RestaurantsDb instance.*/

    } */

}