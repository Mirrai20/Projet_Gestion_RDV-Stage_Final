<html>
    <head>
       <meta charset="utf-8">
        <!-- importer le fichier de style -->
        <link rel="stylesheet" href="css/modifierMyInfo.css" media="screen" type="text/css" />
    </head>
    <body>
        <div id="Box_EDIT_info">
            <!-- zone de modification -->

            <form action="actions/updatePassword.php" method="POST" class="Edit_Info">
                <h1>EDIT MY ACCOUNT</h1>

                <label><b>Email:</b></label>
                <input type="email" name="update_email" value="<?= $admin->getEmail() ?>" class="Edit_Email_pswd" required>

                <label><b>Ancien mot de passe:</b></label>
                <input type="password" placeholder="Entrer le mot de passe" name="password_old" class="Edit_Email_pswd" required>
				        <label><b>Nouveau mot de passe:</b></label>
                <input type="password" pattern=".{5,20}" placeholder="confirmer le mot de passe" name="password_new" class="Edit_Email_pswd" >
                <label><b>Confirmer mot de passe:</b></label>
                <input type="password" pattern=".{5,20}" placeholder="confirmer le mot de passe" name="password_confirm" class="Edit_Email_pswd" >

                <input type="submit" name="BtnEdit_Info" id="BtnEdit_Info" value="MODIFER">

            </form>
        </div>
    </body>
</html>
