package com.example.cupcake.model



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

private const val PRICE_PER_CUPCAKE = 2.0
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.0

class OrderViewModel : ViewModel() {

    private val _quantity: MutableLiveData<Int> = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    private val _flavor: MutableLiveData<String> = MutableLiveData<String>()
    val flavor: LiveData<String> = _flavor

    private val _date: MutableLiveData<String> = MutableLiveData<String>()
    val date: LiveData<String> = _date

    private val _price: MutableLiveData<Double> = MutableLiveData<Double>()
    val price: LiveData<String> =  Transformations.map(_price) { value ->
        NumberFormat.getCurrencyInstance().format( value )
    }

    var dateOptions: List<String> = listOf()

    init {
        dateOptions = getPickupOptions()
        resetOrder()
    }

    fun setQuantity( numberCupcakes: Int){
        _quantity.value = numberCupcakes
        //ACTUALIZA EL PRECIO
        updatePrice()
    }

    fun setFlavor(desiredFlavor: String){
        _flavor.value = desiredFlavor
    }

    fun setDate( pickupDate: String){
        //SE SETEA LA FECHA
        _date.value = pickupDate
        //Y SI ES LA FECHA ACTUAL SE SETEA EL PRECIO
        updatePrice()
    }

    private fun updatePrice(){
        //SE CALCULA EL PRECIO DE LOS CUPCAKES POR EL VALOR DE CADA UNO
        var calculatedPrice = (quantity.value ?: 0) * PRICE_PER_CUPCAKE
        if( dateOptions[0] == date.value){
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        _price.value = calculatedPrice
    }

    fun hasNoFlavorSet() : Boolean{
        return _flavor.value.isNullOrEmpty()
    }

    //MÉTODO QUE RETORNA LAS FECHAS DISPONIBLES
    private fun getPickupOptions(): List<String>{
        //LISTA DE FECHAS A RETORNAR
        val options: MutableList<String> = mutableListOf<String>()
        //FORMATO PARA LA FECHA
        val formatter:SimpleDateFormat = SimpleDateFormat("E MMM d", Locale.getDefault())
        //SE OBTIENE LA FECHA ACTUAL
        val calendar: Calendar = Calendar.getInstance()
        //CREA Y AÑADE A LA LISTA 4 FECHAS FORMATEADAS Y VA AUMENTANDO EL DIA A calendar
        repeat(4){
            options.add( formatter.format( calendar.time ) )
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }

    //REESTABLECER VALORES
    fun resetOrder(){
        _quantity.value = 0
        _flavor.value = ""
        _date.value = dateOptions[0]
        _price.value = 0.0
    }

}