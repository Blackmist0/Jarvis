package com.jarvis.android

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var status: TextView
    private lateinit var voiceButton: Button
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        status = findViewById(R.id.status)
        voiceButton = findViewById(R.id.voiceButton)

        textToSpeech = TextToSpeech(this, this)

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)

        voiceButton.setOnClickListener {
            startVoiceRecognition()
        }
    }

    override fun onInit(statusCode: Int) {
        if (statusCode == TextToSpeech.SUCCESS) {
            textToSpeech.language = Locale.FRENCH
        }
    }

    private fun speak(text: String) {
        textToSpeech.speak(
            text,
            TextToSpeech.QUEUE_FLUSH,
            null,
            "JARVIS_RESPONSE"
        )
    }

    private fun startVoiceRecognition() {

        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.RECORD_AUDIO),
                100
            )
            return
        }

        status.text = "J'écoute..."
        speak("Je t'écoute.")

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )

        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            Locale.FRENCH
        )

        speechRecognizer.setRecognitionListener(
            object : RecognitionListener {

                override fun onResults(results: Bundle?) {

                    val texts =
                        results?.getStringArrayList(
                            SpeechRecognizer.RESULTS_RECOGNITION
                        )

                    val command = texts?.firstOrNull()

                    if (command != null) {

                        status.text = "Tu as dit :\n$command"

                        speak("J'ai compris : $command")

                    } else {

                        status.text = "Je n'ai pas compris."

                        speak("Je n'ai pas compris.")
                    }
                }

                override fun onError(error: Int) {

                    status.text = "Je n'ai pas compris. Réessaie."

                    speak("Je n'ai pas compris. Réessaie.")
                }

                override fun onReadyForSpeech(params: Bundle?) {}

                override fun onBeginningOfSpeech() {}

                override fun onRmsChanged(rmsdB: Float) {}

                override fun onBufferReceived(buffer: ByteArray?) {}

                override fun onEndOfSpeech() {}

                override fun onPartialResults(
                    partialResults: Bundle?
                ) {}

                override fun onEvent(
                    eventType: Int,
                    params: Bundle?
                ) {}
            }
        )

        speechRecognizer.startListening(intent)
    }

    override fun onDestroy() {

        speechRecognizer.destroy()

        textToSpeech.stop()
        textToSpeech.shutdown()

        super.onDestroy()
    }
}
