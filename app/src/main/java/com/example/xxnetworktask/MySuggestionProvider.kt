package com.example.xxnetworktask

import android.content.SearchRecentSuggestionsProvider

class MySuggestionProvider : SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        const val AUTHORITY = "com.example.xxnetworktask.MySuggestionProvider"
        const val MODE: Int = DATABASE_MODE_QUERIES
    }
}