package com.example.myaactv7.viewmodel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myaactv7.data.entity.Mahasiswa
import com.example.myaactv7.data.repository.RepositoryMhs
import com.example.myaactv7.ui.customWidget.TopAppBar
import com.example.myaactv7.ui.navigation.DestinasiUpdate
import com.example.myaactv7.ui.view.mahasiswa.InsertBodyMhs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateMhsViewModel (
    savedStateHandle: SavedStateHandle,
    val repositoryMhs: RepositoryMhs
) : ViewModel(){

    var updateUIState by mutableStateOf(MhsUIState())
        private  set

    private  val  nim: String = checkNotNull(savedStateHandle[DestinasiUpdate.NIM])


    init {
        viewModelScope.launch {
            updateUIState = repositoryMhs.getMhs(nim)
                .filterNotNull()
                .first()
                .toUIStateMhs()
        }
    }

    fun updateState(mahasiswaEvent: MahasiswaEvent) {
        updateUIState = updateUIState.copy(
            mahasiswaEvent = mahasiswaEvent,
        )
    }

    fun validateFields(): Boolean{
        val event = updateUIState.mahasiswaEvent
        val errorState = FormErrorState(
            nim = if (event.nim.isNotEmpty()) null else "NIM tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            jenisKelamin = if (event.jenisKelmain.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat tidak boleh kosong",
            kelas = if (event.kelas.isNotEmpty()) null else "Kelas tidak boleh kosong",
            angkatan = if (event.angkatan.isNotEmpty()) null else "Angkatan tidak boleh kosong",

        )
        updateUIState = updateUIState.copy(isEntryValid = errorState)
        return  errorState.isValid()
    }

    fun updateData(){
        val  currentEvent = updateUIState.mahasiswaEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryMhs.updateMhs(currentEvent.toMahasiswaEntity())
                    updateUIState = updateUIState.copy(
                        snackbarMessage = "Data berhasil diupdate",
                        mahasiswaEvent = MahasiswaEvent(),
                        isEntryValid = FormErrorState()
                    )
                    println("snackBarMessage diatur: ${updateUIState.snackbarMessage}")

                } catch (e: Exception){
                    updateUIState = updateUIState.copy(
                        snackbarMessage = "Data gagal diupdate"

                    )
                }
            }
        } else {
            updateUIState = updateUIState.copy(
                snackbarMessage = "Data gagal diupdate"
            )
        }
    }

    fun resetSnackBarMessage(){
        updateUIState = updateUIState.copy(snackbarMessage = null)
    }

}

fun Mahasiswa.toUIStateMhs(): MhsUIState = MhsUIState(
    mahasiswaEvent = this.toDetailUiEvent(),
)

@Composable
fun UpdateMhsView(
    onBack: () -> Unit,
    OnNavigation: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateMhsViewModel = viewModel(factory = PenyediaViewModel.Factory) //Inisialisasi ViewModel

){
    val uiState = viewModel.updateUIState //Ambil UI state dari ViewModel
    val  snackbarHostState = remember { SnackbarHostState() } // SnackBar state
    val coroutineScope = rememberCoroutineScope()

    // Obervasi perubahan snackBarMessage

    LaunchedEffect(uiState.snackbarMessage) {
        println("LaunchedEffect triggered")
        uiState.snackbarMessage?.let { message->
            println("Snackbar message received: $message")
            coroutineScope.launch {
                println("Launching coroutine for snackbar")
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Long
                )
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }, //Tempatkan snackbar di scallfod
        topBar = {
            TopAppBar(
                onBack = onBack,
                showBackButton =  true,
                judul =  " Edit Mahasiswa",
                modifier = modifier,
            )
        }
    ){ padding ->
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(16.dp)
        ){
            //Isi Body
            InsertBodyMhs(
                uiState = uiState,
                onValueChange = { updateEvent ->
                    viewModel.updateState(updateEvent) //Update state di ViewModel
                },
                onClick = {
                    coroutineScope.launch {
                        if (viewModel.validateFields()) {
                            viewModel.updateData()
                            delay(600)
                            withContext(Dispatchers.Main){
                                OnNavigation() //Navigasi di nain thread
                            }
                        }
                    }
                }
            )
        }
    }
}