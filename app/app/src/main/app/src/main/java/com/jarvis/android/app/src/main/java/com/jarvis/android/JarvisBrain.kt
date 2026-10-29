package com.jarvis.android

class JarvisBrain {

    fun processCommand(command: String): String {

        val text = command.lowercase(Locale.getDefault())

        return when {

            text.contains("bonjour") ||
            text.contains("salut") -> {
                "Bonjour. Je suis JARVIS. Comment puis-je t'aider ?"
            }

            text.contains("qui es-tu") ||
            text.contains("présente-toi") -> {
                "Je suis JARVIS, ton assistant personnel."
            }

            text.contains("merci") -> {
                "Avec plaisir."
            }

            text.contains("au revoir") -> {
                "À bientôt."
            }

            else -> {
                "J'ai reçu ta commande : $command. Mon intelligence en ligne sera bientôt connectée."
            }
        }
    }
}
