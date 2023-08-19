package com.example.newscenter.ui.model

import androidx.lifecycle.ViewModel
import com.example.newscenter.db.App
import com.example.newscenter.db.Favorite
import com.example.newscenter.db.News
import com.example.newscenter.db.User
import com.example.newscenter.spider.NewsItem
import com.example.newscenter.spider.Parser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewModel : ViewModel() {

    private val _username = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _dialogState = MutableStateFlow(false)
    private val _newsList = MutableStateFlow(listOf<News>())
    private val _currentNews: MutableStateFlow<News?> = MutableStateFlow(null)
    private val _currentUser: MutableStateFlow<User?> = MutableStateFlow(null)
    private val _currentFavorites = MutableStateFlow(mutableListOf<Favorite?>())
    private val _selectedTabIndex = MutableStateFlow(0)
    private val _recommends = MutableStateFlow(mutableListOf<News>())
    val recommends: StateFlow<List<News>> = _recommends.asStateFlow()
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex.asStateFlow()
//    private val _weatherState:MutableStateFlow<String> = MutableStateFlow("")
    val newsList: StateFlow<List<News>> = _newsList.asStateFlow()
    val username: StateFlow<String> = _username.asStateFlow()
    val password: StateFlow<String> = _password.asStateFlow()
    val dialogState: StateFlow<Boolean> = _dialogState.asStateFlow()
    val currentNews: StateFlow<News?> = _currentNews.asStateFlow()
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()
    val currentFavorites: StateFlow<MutableList<Favorite?>> = _currentFavorites.asStateFlow()
    private val parser = Parser()

    fun onNameChange(msg: String) {
        _username.value = msg
    }

    fun onPassChange(msg: String) {
        _password.value = msg
    }

    fun onUserChange(user: User) {
        _currentUser.value = user
    }

    fun onFavoritesChange(favorites: MutableList<Favorite?>) {
        _currentFavorites.value = favorites
    }


    fun openDialog() {
        _dialogState.value = true
    }

    fun closeDialog() {
        _dialogState.value = false
    }

    fun setNews(news: List<News>) {
        _newsList.value = news
    }

    fun setNewsContent(news: News) {
        if (news.content == null) {
            news.content = parser.getContent(news.docurl)
            App.db.newsDao().update(news.title, news.content!!)
        }
        _currentNews.value = news

    }

    fun setFavorContent(news: News) {
        _currentNews.value = news
    }

    fun setTab(tabIndex: Int) {
        _selectedTabIndex.value = tabIndex
    }
    fun addRecommends(items:List<News>) {
        _recommends.value.addAll(items)
    }
}