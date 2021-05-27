<!-- Edite date RDV -->
    <div class="sign-box">

        <div class="upd_choice" style="background-color: #318700;">
            <h2>Modifier la date du Rendez-vous</h2>
            <h3><?= $afficher_rdv[$i]->getDateRdv() ?></h3>
            <?php
                //  $daterdv=array();
                  $daterdv = explode(" ", $afficher_rdv[$i]->getDateRdv());
              ?>
        </div>

          <div class="edit-user" style="padding-top: 9px;" >
                    <br><br>
                    <label for="EDIT_jourRdv">Le jour du Rendez-vous :</label>
                    <input type="date" name="EDIT_jourRdv" id="EDIT_jourRdv" min="<?= date("Y-m-d") ?>" max="2030-01-01" value="<?= $daterdv[0] ?>"required>

                    <label for="EDIT_dateRdv">La date du Rendez-vous (step +20Min) :</label>
                    <input type="time" placeholder="HH:MM" name="EDIT_dateRdv" id="EDIT_dateRdv" min="09:00" max="17:00" step="1200"value="<?= $daterdv[1] ?>" required>


                    <input type="submit" class="btl btnEdit" name="edit_rdv" value="Modifier" id="Corriger_submit" >

                    <p class="errors" id="Corriger_errors"><br></p>

        </div>



    </div>
