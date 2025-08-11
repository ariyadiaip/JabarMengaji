package edu.ariyadi.jabarmengaji.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import edu.ariyadi.jabarmengaji.data.model.Community
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CommunityRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    fun getAllCommunities(): Flow<List<Community>> = flow {
        try {
            val snapshot = firestore.collection("communities").get().await()
            val communities = snapshot.toObjects(Community::class.java)
            emit(communities)
        } catch (e: Exception) {
            emit(emptyList())
            Log.e("CommunityRepository", "Error getting communities: ${e.message}")
        }
    }

    fun searchCommunities(query: String): Flow<List<Community>> = flow {
        try {
            if (query.isBlank()) {
                val snapshot = firestore.collection("communities").get().await()
                val communities = snapshot.toObjects(Community::class.java)
                emit(communities)
            } else {
                val lowercaseQuery = query.lowercase()

                val snapshot = firestore.collection("communities")
                    .whereArrayContains("nameKeywords", lowercaseQuery)
                    .get()
                    .await()
                val communities = snapshot.toObjects(Community::class.java)
                emit(communities)
            }
        } catch (e: Exception) {
            emit(emptyList())
            Log.e("CommunityRepository", "Error searching communities: ${e.message}")
        }
    }

}