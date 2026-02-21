


  
  
    
## old    
    ## Mitä Retrofit tekee
    
    ### Se muuttaa REST päätepisteet Kotlin käyttöliittymän funktioiksi 
    
    ## Miten JSON muutetaan dataluokiksi
    
    ### Retrofit käyttää GSON muuntajaa, joka muuttaa JSON vastaukset automaattisesti Kotlin dataluokiksi
    
    ## Miten coroutines toimii tässä
    
    ### Coroutinet tekee API kutsun ViewModelissa taustasäikeessä ja UI päivittyy kun data tulee
    
    ## Miten UI-tila toimii
    
    ### ViewModel säilytää UI:n tilan StateFlow:ssa ja Compose päivittää käyttöliittymää sen mukaan
    
    ## Miten API-key on tallennettu
    
    ### API avain on local.properties tiedostassa, joka sitten haetaan BuildConfig:n kautta
