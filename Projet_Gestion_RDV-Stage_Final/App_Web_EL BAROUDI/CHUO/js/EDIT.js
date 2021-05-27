<!-- CE SCRIPT VA GERER LES INPUT DANS LE CAS D'AJOUTS ou DE modifications -->
<script type="text/javascript">
let verifInputs = () => {
            name = document.getElementById('EDIT_name<?=$i?>').value;
            prenom = document.getElementById('EDIT_prenom<?=$i?>').value;
            telephone = document.getElementById('EDIT_tele<?=$i?>').value;
            email = document.getElementById('EDIT_email<?=$i?>').value;
            password = document.getElementById('EDIT_password<?=$i?>').value;

            errors = document.getElementById('EDIT_errors<?=$i?>');


            if(name.trim().length == 0) {
                errors.innerHTML = '* Name cannot be empty';
            }else{
              if(prenom.trim().length == 0) {
                  errors.innerHTML = '* Prenom cannot be empty';
              }else{
                  if(telephone.length == 0){
                        errors.innerHTML = '* Telephone cannot be empty';
                  }else{
                      if(!(/^[0]{1}[6-7]{1}[0-9]{8}$/.test(telephone))){
                            errors.innerHTML = '* Telephone given is not valid';
                      }else {

                          if(!(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email))) {
                                errors.innerHTML = '* Email given is not valid';
                              }else {
                                  if(password.length == 0) {
                                       errors.innerHTML = '* Password cannot be empty';
                                   }else {
                                      if(password.length < 5) {
                                          errors.innerHTML = '* Password given is too short';
                                        }else {
                                          return true;
                                        }
                                   }
                                }
                            }
                        }
                      }
                  }
                  return false;
              }

</script>
