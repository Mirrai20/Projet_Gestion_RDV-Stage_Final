    <!-- EDIT USER  -->
        <div class="sign-box">

            <div class="upd_choice" style="background-color: #318700;">
                <h2>Modifications</h2>
            </div>

              <div class="edit-user" >

                        <div class="sign_name">
                            <div style="padding-right: 4%;">
                              <label for="EDIT_name">Nom :</label>
                              <input type="text" name="EDIT_name" id="EDIT_name" pattern=".{3,20}" value="<?= $afficher_user[$i]->getNom() ?>" required>
                            </div>

                            <div>
                              <label for="EDIT_prenom">Prenom :</label>
                              <input type="text" name="EDIT_prenom" id="EDIT_prenom" pattern=".{3,20}" value="<?= $afficher_user[$i]->getPrenom() ?>" required>
                            </div>
                        </div>

                        <label for="EDIT_date">Date de Naissance :</label>
                        <input type="date" name="EDIT_date" id="EDIT_date" value="<?= $afficher_user[$i]->getdateN() ?>" min="1900-01-01" max="2050-1-1" required>



                        <label for="EDIT_tele">Numéro de téléphone :</label>
                        <input type="tel" pattern="[0]{1}[6-7]{1}[0-9]{8}" name="EDIT_tele" id="EDIT_tele" value="<?= $afficher_user[$i]->getTelephone() ?>" required>

                        <label for="EDIT_email">Email :</label>
                        <input type="email" name="EDIT_email" id="EDIT_email" value="<?= $afficher_user[$i]->getEmail() ?>" required>

                        <label for="EDIT_password">Password :</label>
                        <input type="password" name="EDIT_password" pattern=".{5,20}" id="EDIT_password" value="<?= $afficher_user[$i]->getPassword() ?>" required>

                        <label for="EDIT_code" id="edLc">CIN :</label>
                        <input type="text" name="EDIT_code" pattern=".{4,20}"  id="EDIT_code" value="<?= $afficher_user[$i]->getCIN() ?>">

                        <div class="sign-selects">
                            <div style="padding-right: 4%;">
                                <label for="EDIT_description">Description :</label>
                                <select name="EDIT_description" id="EDIT_description" >
                                  <?php  if($afficher_user[$i]->getDescription() == 'Patient' ) : ?>
                                    <option value="Etudiant"<?php if($afficher_user[$i]->getDescription() == "Patient") echo "selected"; ?>>Patient</option>

                                <?php else : ?>
                                    <option value="Enseignant"<?php if($afficher_user[$i]->getDescription() == "Medecin") echo "selected"; ?>>Medecin</option>
                                <?php endif; ?>

                                </select>
                                <?php  if($afficher_user[$i]->getDescription() == 'Medecin' ) : ?>
                                        <label for="EDIT_sp">Spécialité :</label>
                                        <select name="EDIT_sp" id="EDIT_sp">
                                            <option value="Angiologue" <?php if($afficher_user[$i]->getSpecialite() == "Angiologue") echo "selected"; ?>>Angiologue</option>
                                            <option value="Cardiologue" <?php if($afficher_user[$i]->getSpecialite() == "Cardiologue") echo "selected"; ?>>Cardiologue</option>
                                            <option value="Diabetologue" <?php if($afficher_user[$i]->getSpecialite() == "Diabetologue") echo "selected"; ?>>Diabétologue</option>
                                        </select>
                                <?php else : ?>
                                          <label for="EDIT_sx">Sex :</label>
                                          <select name="EDIT_sx" id="EDIT_sx">
                                              <option value="Femme" <?php if($afficher_user[$i]->getSex() == "Femme") echo "selected"; ?>>Femme</option>
                                              <option value="Homme" <?php if($afficher_user[$i]->getSex() == "Homme") echo "selected"; ?>>Homme</option>
                                          </select>
                                <?php endif; ?>
                            </div>
                        </div>
                        <?php  if($afficher_user[$i]->getDescription() == 'Medecin' ) : ?>
                          <label for="EDIT_Numedecin"  >Nu Médecin :</label>
                          <input type="text" pattern=".{3,20}" name="EDIT_Numedecin"  id="EDIT_Numedecin" value="<?= $afficher_user[$i]->getNuMedecin() ?>" >
                          <label for="EDIT_cg">Conge :</label>
                          <select name="EDIT_cg" id="EDIT_cg">
                              <option value="Oui" <?php if($afficher_user[$i]->getConge() == "Oui") echo "selected"; ?>>Oui</option>
                              <option value="Non" <?php if($afficher_user[$i]->getConge() == "Non") echo "selected"; ?>>Non</option>
                          </select>
                        <?php endif; ?>

                        <input type="submit" class="btl btnEdit" name="EDIT_submit" value="EDIT" id="EDIT_submit" onclick="return verifInputs()">

                        <p class="errors" id="EDIT_errors"><br></p>

            </div>



        </div>
