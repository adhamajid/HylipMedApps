package com.windranger.reminder.di

import com.windranger.reminder.service.AlarmService
import com.windranger.reminder.service.AlarmServiceStarter
import com.windranger.reminder.ui.alarm.AddAlarmActivity
import com.windranger.reminder.ui.alarm.AlarmActivity
import com.windranger.reminder.ui.auth.login.LoginActivity
import com.windranger.reminder.ui.auth.register.RegisterActivity
import com.windranger.reminder.ui.desease.DeseaseActivity
import com.windranger.reminder.ui.efek.EfekActivity
import com.windranger.reminder.ui.main.MainActivity
import com.windranger.reminder.ui.main.SplashActivity
import com.windranger.reminder.ui.medicine.add.AddMedicineActivity
import com.windranger.reminder.ui.note.NoteActivity
import com.windranger.reminder.ui.note.add.AddNoteActivity
import com.windranger.reminder.ui.notif.NotifActivity
import com.windranger.reminder.ui.notif.alarm.NotifAlarmActivity
import com.windranger.reminder.ui.questionaire.QuestionaireActivity
import com.windranger.reminder.ui.terapi.TerapiActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {
    @ContributesAndroidInjector
    internal abstract fun loginActivity(): LoginActivity

    @ContributesAndroidInjector
    internal abstract fun splashActivity(): SplashActivity

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun registerActivity(): RegisterActivity

    @ContributesAndroidInjector
    internal abstract fun deseaseActivity(): DeseaseActivity

    @ContributesAndroidInjector
    internal abstract fun terapiActivity(): TerapiActivity

    @ContributesAndroidInjector
    internal abstract fun efekActivity(): EfekActivity

    @ContributesAndroidInjector
    internal abstract fun addMedicineActivity(): AddMedicineActivity

    @ContributesAndroidInjector
    internal abstract fun notifActivity(): NotifActivity

    @ContributesAndroidInjector
    internal abstract fun alarmServiceStarter(): AlarmServiceStarter

    @ContributesAndroidInjector
    internal abstract fun alarmService(): AlarmService

    @ContributesAndroidInjector
    internal abstract fun addAlarmActivity(): AddAlarmActivity

    @ContributesAndroidInjector
    internal abstract fun alarmActivity(): AlarmActivity

    @ContributesAndroidInjector
    internal abstract fun notifAlarmActivity(): NotifAlarmActivity

    @ContributesAndroidInjector
    internal abstract fun questionaireActivity(): QuestionaireActivity

    @ContributesAndroidInjector
    internal abstract fun noteActivity(): NoteActivity

    @ContributesAndroidInjector
    internal abstract fun addNoteActivity(): AddNoteActivity
}