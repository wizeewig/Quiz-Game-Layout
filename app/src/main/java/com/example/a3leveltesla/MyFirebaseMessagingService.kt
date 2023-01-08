package com.example.a3leveltesla

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage



const val channelId="notification channel"
const val channelName="NotificationCompat.Builder"
class MyFirebaseMessagingService :FirebaseMessagingService(){

    //generate the notification
    //attach the notif created with custom layout
    //show the notif


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if(remoteMessage.getNotification() !=null){
            generateNotification(remoteMessage.notification!!.title!!, remoteMessage.notification!!.body!!)
        }
    }

    fun getRemoteView(title: String,description: String): RemoteViews {
        val remoteView= RemoteViews("NotificationCompat.Builder",R.layout.activity_notif)

        remoteView.setTextViewText(R.id.title,title)
        remoteView.setTextViewText(R.id.description,description)
        remoteView.setImageViewResource(R.id.app_logo,R.drawable.notif_photo)

        return remoteView
    }

        fun generateNotification(title:String, description:String){
            val intent=Intent(this,MainActivity::class.java)
            intent.addFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP))
            val pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT)

            //channel id, channel name

            var builder:NotificationCompat.Builder=NotificationCompat.Builder(applicationContext,
                channelId)
                .setSmallIcon(R.drawable.notif_photo)
                .setAutoCancel(true)
                .setVibrate(longArrayOf(1000,1000,1000,1000))
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)

            builder =builder.setContent(getRemoteView(title,description))


            val notificationManager= getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val notificationChannel= NotificationChannel(channelId, channelName,NotificationManager.IMPORTANCE_HIGH)
                notificationManager.createNotificationChannel(notificationChannel)
            }

            notificationManager.notify(0,builder.build())
        }
}