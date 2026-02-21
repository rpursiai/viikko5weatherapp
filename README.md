## week6
## mitä Room tekee (Entity–DAO–Database–Repository–ViewModel–UI)

### room hoitaa sovelluksen tietojen tallenuksen paikallisesti. 
  - entity määrittää tietokannan taulun
  - DAO sisältää tietokantaan tehdyt kyselyt yms.
  - database yhdistää DAO ja entityn
  - repository käyttää DAO:ta ja tarjoaa UI:lle Flow-/suspend-funktiot
  - viewdmodel hoitaa sovelluksen logiikkaa ja tilaa
  - ui näyttää viewmodelin datan

## projektisi rakenne

###  projektin rakenne on:
  - data/model sisältää dataluokat
      - API response mallit (WeatherResponse)
      - room entity WeatherEntity
  - data/local room tietokanta juttuja
      - WeatherDao tietokanta kyselyille
      - AppDatabase luo Room tietonnan instannssin
  - data/repository WeatherRepository päättää mitä dataa tulee Roomista tai APIsta ja hoitaa välimuisti logiikan
  - viewmodel UI tila ja sovelluksen logiikka
  - ui jetpack compose ruudut

## miten datavirta kulkee

### room lähettää datab muutokset eteenpäin automaattiseseti, viewmodel vastaanottaa datan, compose UI seuraa tätä collectAsState() toiminnolla ja päivittää näkymää automaattisesti kun data muuttuu

## (Sää) miten välimuistilogiikka toimii

### kun käyttäjä hakee säätä jostain paikasta, niin weatherrepository tarkistaa välimuistissa olevan datan iän, jos data on alle 30 minuuttia vanha niin sitä käytetään room:sta, muuten uusi API kutsu tehdään ja data tallennetaan room:n
  

--------------------------
    
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
