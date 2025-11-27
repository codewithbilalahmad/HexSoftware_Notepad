package com.muhammad.notepad.di

import androidx.room.Room
import com.muhammad.notepad.data.local.NotepadDatabase
import com.muhammad.notepad.data.repository.NoteRepositoryImp
import com.muhammad.notepad.domain.repository.NoteRepository
import com.muhammad.notepad.presentation.screens.home.HomeViewModel
import com.muhammad.notepad.utils.Constants.DATABASE_NAME
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            get(),
            NotepadDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration(true).setQueryCoroutineContext(Dispatchers.IO).build()
    }
    single {
        get<NotepadDatabase>().noteDao()
    }
    single {
        NoteRepositoryImp(get())
    }.bind<NoteRepository>()
    viewModelOf(::HomeViewModel)
}