package com.example.cupcake

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cupcake.model.OrderViewModel
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.text.NumberFormat

class ViewModelTests {

    //ESTO INDICA QUE SE DEBE HACER ANTES DE CADA PRUEBA
    @get:Rule
    //ESTO INDICA QUE LOS OBJETOS LIVE DATA NO DEBEN LLAMAR A EL HILO PRINCIPAL
    //ESTO ESTA DISPONIBLE GRACIAS A LA DEPENDENCIA DE  ->  testImplementation 'androidx.arch.core:core-testing:2.1.0'
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    //VALIDAMOS QUE AL ACTUALIZAR LA CANTIDAD, ESTA SE ASIGNE CORRECTAMENTE AL VIEWMODEL
    @Test
    fun quantity_twelve_cupcakes(){
        //OBTENEMOS REFERENCIA AL VIEWMODEL
        val viewModel: OrderViewModel = OrderViewModel()
        //SIEMPRE DEBEMOS OBSERVAR LA VARIABLE
        viewModel.quantity.observeForever {  }
        //LLAMAMOS AL MÉTODO DEL VIEWMODEL QUE ACTUALIZA LA PROPIEDAD
        viewModel.setQuantity(12)
        //VERIFICAMOS QUE LA PROPIEDAD quantity DEL VIEWMODEL SE ACTUALICE CORRECTAMENTE
        assertEquals(12, viewModel.quantity.value)
    }

    //SE VA A VALIDAR CUANDO SE ASIGNE LA CANTIDAD DE CUPCAKES EL VALOR CALCULADO SEA CORRECTO
    @Test
    fun price_twelve_cupcakes(){
        //OBTENEMOS EL VIEWMODEL
        val viewModel: OrderViewModel = OrderViewModel()
        //OBSERVAMOS LA VARIABLE A PROBAR
        viewModel.price.observeForever {  }
        //INVOCAMOS EL METODO, QUE INTERNAMENTE LLAMA AL mÉTODO QUE ACTUALIZA EL PRECIO
        viewModel.setQuantity(12)
        //FORMATEAMOS EL VALOR QUE QUEREMOS PROBAR
        val testNumber = NumberFormat.getCurrencyInstance().format(27.00)
        //VERIFICAMOS QUE LOS VALORES COINCIDAN
        assertEquals(testNumber, viewModel.price.value)
    }

}