
let checkdesc = (choice) => {
   switch(choice) {
          case 'Patient':
            document.querySelector('.Spe').style.display="none";
            document.querySelector('.NuMed').style.display="none";
            document.getElementById('signup_sx').style.display="block";
           break;

           case 'Medecin':
           document.getElementById('signup_sx').style.display="none";
           document.querySelector('.Spe').style.display="block";
           document.querySelector('.NuMed').style.display="block";

            break;


   }
}
let checkInputs = (element, choice) => {
    event.preventDefault();
    switch(choice) {
        case 'in':
            email = document.getElementById('signin_email').value;
            password = document.getElementById('signin_password').value;
            errors = document.getElementById('signin_errors');

            errors.innerHTML = '<br>';

            if(!(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email))) {
                errors.innerHTML += '* Email donné n\'est pas valide';
            } else if(password.length < 5) {
                errors.innerHTML += '* Password donné est très court';
            } else {
                element.parentNode.submit();
            }

            break;

        case 'up':
            name = document.getElementById('signup_name').value;
            prenom = document.getElementById('signup_prenom').value;
            telephone = document.getElementById('signup_tele').value;
            email = document.getElementById('signup_email').value;
            password = document.getElementById('signup_password').value;
            dateN = document.getElementById('signup_date').value;
            code = document.getElementById('signup_code').value;

            errors = document.getElementById('signup_errors');

            errors.innerHTML = '';
            let isValidDate = Date.parse(dateN);
            if(name.trim().length == 0) {
                errors.innerHTML += '* Nom ne peut pas etre vide';
            }else{
              if(prenom.trim().length == 0) {
                  errors.innerHTML += '* Prenom ne peut pas etre vide';
              }else{
                if(isNaN(isValidDate) || dateN<'1900-01-01' || dateN>'2050-1-1' ){
                  errors.innerHTML += "* La date de Naissance que tu as tapé n'est pas valide";
                }else{
                  if(telephone.length == 0){
                        errors.innerHTML += '* Telephone ne peut pas etre vide';
                  }else{
                      if(!(/^[0]{1}[6-7]{1}[0-9]{8}$/.test(telephone))){
                            errors.innerHTML += '* Telephone donné n\'est pas valide';
                      }else {
                        if(code.length == 0){
                              errors.innerHTML += '* CIN ne peut pas etre vide';
                        }else{
                            if(code.length < 5){
                                  errors.innerHTML += '* CIN donné est très court';
                            }else {
                                if(!(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email))) {
                                  errors.innerHTML += '* Email donné n\'est pas valide';
                                }else {
                                  if(password.length == 0) {
                                       errors.innerHTML += '* Password ne peut pas etre vide';
                                   }else {
                                      if(password.length < 5) {
                                          errors.innerHTML += '* Password donné est très court';
                                        }else{
                                          element.parentNode.submit();
                                        }
                                   }
                                }
                            }
                        }
                      }
                  }
              }
            }
          }
            break;
    }
}
