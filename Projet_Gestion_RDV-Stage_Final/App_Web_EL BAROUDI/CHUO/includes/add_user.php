
    <!-- ADD NEW USER  -->
        <div class="sign-box">

            <div class="upd_choice">
                <h2>ADD NEW USER</h2>
            </div>

              <div class="add-user" >
                    <form action="actions/ajouter_user.php" method="POST">


                        <div class="sign_name">
                            <div style="padding-right: 4%;">
                              <label for="signup_name">Nom :</label>
                              <input type="text" name="signup_name" id="signup_name" required>
                            </div>

                            <div>
                              <label for="signup_prenom">Prenom :</label>
                              <input type="text" name="signup_prenom" id="signup_prenom" required>
                            </div>
                        </div>

                        <label for="signup_date">Date de Naissance :</label>
                        <input type="date" name="signup_date" id="signup_date" value="yyyy-mm-dd" min="1900-01-01" max="2001-12-31">

                        <label for="signup_tele">Numéro de téléphone :</label>
                        <input type="tel" pattern="[0]{1}[6-7]{1}[0-9]{8}" name="signup_tele" id="signup_tele" required>

                        <label for="signup_email">Email :</label>
                        <input type="email" name="signup_email" id="signup_email" required>

                        <label for="signup_password">Password :</label>
                        <input type="password" name="signup_password" id="signup_password" required>

                        <label for="signup_code" id="idLc">CIN :</label>
                        <input type="text" name="signup_code" id="signup_code" required>

                        <div class="sign-selects">
                            <div style="padding-right: 4%;">
                                <label for="signup_description">Description :</label>
                                <select name="signup_description" id="signup_description"  onclick="checkdesc(value)">
                                    <option value="Patient">Patient</option>
                                    <option value="Medecin">Medecin</option>

                                </select>
                            </div>

                            <div class="Spe" >
                                <label for="signup_sp">Spécialité :</label>
                                <select name="signup_sp" id="signup_sp">
                                    <option value="Angiologue">Angiologue</option>
                                    <option value="Cardiologue">Cardiologue</option>
                                    <option value="Diabetologue">Diabétologue</option>
                                </select>
                            </div>
                            <div id="signup_sx">
                                  <label for="signup_sx">Sex :</label>
                                  <select name="signup_sx" >
                                      <option value="Femme">Femme</option>
                                      <option value="Homme">Homme</option>
                                  </select>
                            </div>
                        </div>

                        <div class="NuMed">
                          <label for="signup_Numedecin"  >Nu Médecin :</label>
                          <input type="text" name="signup_Numedecin" id="signup_Numedecin" required>
                        </div>

                        <input type="submit" class="btl" name="signup_submit" value="ADD USER" onclick="checkInputs(this, 'up')">

                        <p class="errors" id="signup_errors"><br></p>

                    </form>
            </div>

        </div>
        <!-- CE SCRIPT VA GERER LES INPUT DANS LE CAS D'AJOUTS ou DE modifications -->
        <script src="js/login.js"></script>
