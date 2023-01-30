package de.fhe.ai.flipsen.dependency_injection

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.fhe.ai.flipsen.database.local.PasswordDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
        callback: PasswordDatabase.Callback
    ) = Room.databaseBuilder(app, PasswordDatabase::class.java, "password_database")
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .addCallback(callback)
        .build()

    @Provides
    fun providePasswordDao(db: PasswordDatabase) = db.passwordDao()

    @Provides
    fun providePasswordFolderDao(db: PasswordDatabase) = db.passwordFolderDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope