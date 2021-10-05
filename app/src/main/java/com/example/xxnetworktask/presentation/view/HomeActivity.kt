package com.example.xxnetworktask.presentation.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import com.example.xxnetworktask.MySuggestionProvider
import com.example.xxnetworktask.R
import com.example.xxnetworktask.common.AppConstant.Companion.ACTIVITY_ROLE
import com.example.xxnetworktask.common.AppConstant.Companion.SEARCH_LIST
import com.example.xxnetworktask.common.AppConstant.Companion.SEARCH_QUERY
import com.example.xxnetworktask.common.AppConstant.Companion.WISH_LIST
import com.example.xxnetworktask.common.BaseActivity
import com.example.xxnetworktask.databinding.ActivityHomeBinding
import com.example.xxnetworktask.presentation.viewmodel.HomeViewModel

import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomeActivity : BaseActivity() {

//    @Inject
//    lateinit var homeViewModel: HomeViewModel

    private lateinit var viewBinding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.button.setOnClickListener {
            clearHistory()
        }

    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }


    private fun handleIntent(intent: Intent) {
        val suggestionProvider = SearchRecentSuggestions(
            this,
            MySuggestionProvider.AUTHORITY,
            MySuggestionProvider.MODE
        )
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                doMySearch(query)
                suggestionProvider.saveRecentQuery(query, null)

            }
        }

    }

    private fun clearHistory() {
        SearchRecentSuggestions(this, MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE)
            .clearHistory()
    }

    private fun doMySearch(query: String) {
        val intent = Intent(this, MovieListActivity::class.java)
        intent.putExtra(ACTIVITY_ROLE, SEARCH_LIST)
        intent.putExtra(SEARCH_QUERY, query)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        // Get the SearchView and set the searchable configuration
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.apply {
            // Assumes current activity is the searchable activity
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false) // Do not iconify the widget; expand it by default
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.getItemId()) {
            R.id.wishList -> {
                val intent = Intent(this, MovieListActivity::class.java)
                intent.putExtra(ACTIVITY_ROLE, WISH_LIST)
                startActivity(intent)
                return true
            }

        }
        return super.onOptionsItemSelected(item);
    }
}