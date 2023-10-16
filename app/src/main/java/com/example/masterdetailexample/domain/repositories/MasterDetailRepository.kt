package com.example.masterdetailexample.domain.repositories

import com.example.masterdetailexample.common.Constants
import com.example.masterdetailexample.data.remote.api.MasterDetailApi
import com.example.masterdetailexample.domain.models.FirebaseModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MasterDetailRepository @Inject constructor(
    private val api: MasterDetailApi,
    private val firestoreInstance: FirebaseFirestore
) {
   suspend fun getAllPokemon() = api.getAllPokemon()

    suspend fun getAllPoints(): List<FirebaseModel> {
        return firestoreInstance.collection(Constants.COLLECTION_FIRESTORE)
            .get()
            .await()
            .toObjects(FirebaseModel::class.java)
    }

}