<!-- Correct USER info -->
    <div class="sign-box">

        <div class="upd_choice" style="background-color: #318700;">
            <h2>Correction</h2>
        </div>

          <div class="edit-user" >
                    <div>
                      <?php echo "<img src='data:image;base64,".base64_encode($afficher_reclamation[$i]->getCaptureCIN())."' alt='Capture CIN' class='picbig'>"; ?>
                    </div>
                    <div class="sign_name">
                        <div style="padding-right: 4%;">
                          <label for="Corriger_name">Nom :</label>
                          <input type="text" name="Corriger_name" id="Corriger_name" pattern=".{3,20}" value="<?= User::findBy($afficher_reclamation[$i]->getID())->getNom() ?>" required>
                        </div>

                        <div>
                          <label for="Corriger_prenom">Prenom :</label>
                          <input type="text" name="Corriger_prenom" id="Corriger_prenom" pattern=".{3,20}" value="<?= User::findBy($afficher_reclamation[$i]->getID())->getPrenom() ?>" required>
                        </div>
                    </div>

                    <label for="Corriger_date">Date de Naissance :</label>
                    <input type="date" name="Corriger_date" id="Corriger_date" value="<?= User::findBy($afficher_reclamation[$i]->getID())->getdateN() ?>" min="1900-01-01" max="2050-1-1" required>

                    <label for="Corriger_code" id="edLc">CIN :</label>
                    <input type="text" name="Corriger_code" pattern=".{3,20}"  id="Corriger_code" value="<?= $afficher_reclamation[$i]->getID() ?>">

                    <input type="submit" class="btl btnEdit" name="Corriger_submit" value="Modifier" id="Corriger_submit" >

                    <p class="errors" id="Corriger_errors"><br></p>

        </div>



    </div>
