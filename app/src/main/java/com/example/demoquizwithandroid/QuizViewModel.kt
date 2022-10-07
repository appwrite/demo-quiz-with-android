package com.example.demoquizwithandroid

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.appwrite.Client
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Databases
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {
    private val url = "https://10.0.2.2:443/v1"
    private val projectId = "633f811aaef060067cae"
    private val databaseId = "633f812b13dee5884d5f"
    private val collectionId = "633f8131876eaeb964df"
    lateinit var client : Client
    fun create(context: Context) {
        client = Client(context)
            .setEndpoint(url)
            .setProject(projectId)
            .setSelfSigned(true)
        getQuestions()
    }
    private val db by lazy {
        Databases(client)
    }

    private val _questions = MutableLiveData<List<QuestionModel>>().apply { value = null }

    val questions: LiveData<List<QuestionModel>> = _questions
    val selectedQuestion = MutableLiveData<Int>().apply { value = 0}
    val correct = MutableLiveData<Int>().apply { value = 0 }

    private fun getQuestions() {
        viewModelScope.launch {
            try {
                val response = db.listDocuments(databaseId = databaseId, collectionId = collectionId)
                val questions: List<QuestionModel> = response.documents.map { 
                    QuestionModel(
                        question = it.data["question"] as String, 
                        options = it.data["options"] as List<String>, 
                        answer = it.data["answer"] as String
                    )
                }
                _questions.postValue(questions)
            } catch(e : AppwriteException) {
                Log.e("Get questions",e.message.toString())
            }
        }
    }
}