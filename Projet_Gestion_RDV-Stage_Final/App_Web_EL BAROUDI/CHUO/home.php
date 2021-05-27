<?php

	session_start();

    spl_autoload_register(function ($class_name) {
        include 'Classes/'. $class_name . '.php';
    });

    if(!isset($_SESSION['CIN'])) {
        header('Location: /CHUO/');
        die();
    }

	if(isset($_POST['logout_button'])) {
		session_destroy();
		header('Location: /CHUO/');
	}

	$admin = User::findBy($_SESSION['CIN']);

	//initialisation des variables
	if($admin->getDescription()=="Administrateur"){
		$newsUser = array();
		$afficher_user = array();
		$afficher_reclamation	=	array();
		$afficher_medecin	=	array();
		$afficher_rdv	=	array();

		$Administrateur = Administrateur::findBy($admin->getCIN());
		$newsUser = $Administrateur->getNewUser();
		$afficher_user = $Administrateur->getALLuser();
		$afficher_reclamation = $Administrateur->getALLReclamation();
		$afficher_medecin = $Administrateur->getALLmedecin();
		if(!empty($_GET['id'])){
			$afficher_rdv = $Administrateur->getALLRdv($_GET['id']);
			echo "<script> show(3); </script>";
		}

	}
	//ERRORS
	$error = '';
	if(isset($_GET[sha1('email_used')])) $error = 'Email est déja utilisé par un autre compte!';
	if(isset($_GET[sha1('code_used')])) $error = 'CIN est déja utilisé par un autre compte!';
	if(isset($_GET[sha1('Tele_used')])) $error = 'Numéro de Téléphone est déja utilisé par un autre compte!';
	if(isset($_GET[sha1('Nu_medecin_used')])) $error = 'Numéro de medecin est déja utilisé par un autre compte!';
	if(isset($_GET[sha1('exc_problem')])) $error = 'Server issues, please try EDIT again!';
	if(isset($_GET[sha1('password_wrong')])) $error = 'le mot de passe donné est incorrect!';
	if(isset($_GET[sha1('password_confirmation_wrong')])) $error = 'Le mot de passe de confirmation est faux!';
	if(isset($_GET[sha1('RDV_used')])) $error = 'Cette date de Rendez-vous est déja pris par un autre patient!';

	//SUCCESS
	$success = '';
	if(isset($_GET[sha1('user_edit')])) $success = "Vous avez bien modifié cet utilisateur!";
	if(isset($_GET[sha1('user_added')])) $success = "Vous avez bien ajouté un nouveau utilisateur";
	if(isset($_GET[sha1('user_del')])) $success = "Vous avez bien supprimé cet utilisateur!";
	if(isset($_GET[sha1('info_edit')])) $success = "Vous avez bien vos informations!";
	if(isset($_GET[sha1('rdv_del')])) $success = "Vous avez bien Annulé ce Rendez-vous!";
	if(isset($_GET[sha1('RDV_edit')])) $success = "Vous avez bien modifié ce Rendez-vous!";
	if(isset($_GET[sha1('Recl_Co')])) $success = "Vous avez bien Corrigé cette reclamation!";
	if(isset($_GET[sha1('Recl_Rej')])) $success = "Vous avez rejeté cette reclamation!";





?>
<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title>Page Admin</title>
    <link rel="stylesheet" href="css/admin.css">
  </head>
	<?php if(empty($_GET['id'])): ?>
		<body onload="show(0)">
	<?php else : echo "<body onload='show(2)'>"; endif; ?>

		<!-- Erreurs -->
			<div class="global-errors" id="global-errors" onclick="location.href='home.php'">
				<?php
				if($error != "") {
					echo "<p>$error</p>";
				}
				?>
			</div>
			<!-- SUCCESS -->
				<div class="global-success" id="global-success" onclick="location.href='home.php'" >
					<?php
					if($success != "") {
						echo "<p>$success</p>";
					}
					?>
				</div>

    <div id="container">
        <nav id="leftnav">
          <div id="logo">
             <img src="img/logo-accueil.png" alt="CHUO logo-accueil">
          </div>
          <ul id="menu">
            <li> <a id="home">Home</a> </li>
            <li> <a id="users">Users</a> </li>
            <li> <a id="RDV">Rendez-vous</a> </li>
            <li> <a id="reclamation">Réclamation</a> </li>
            <li> <a id="account">Mon compte</a></li>
          </ul>
          <div id="minimize"> &lt; </div>
        </nav>
        <header id="topnav">
            <form action="home.php" method="post">
              <input type="submit" id="Links" name="logout_button" value="Se deconnecter" onclick="return confirm('Etes-vous sur de se déconnecter ?')">
            </form>
        </header>
        <main id="main">
            <div id="maximize"> &gt; </div>
            <div id="actions-container">
              <div id="home-action" style="display:none;">
                  <div class="title">
                    Les nouveaux demandes d'inscription
                  </div>
                  <div class="row">
										<div class="idscrol">
                      <table class='table'>
                          <thead>
                            <th>CIN</th>
                            <th>Nom</th>
                            <th>Prenom</th>
                            <th>Description</th>
                            <th>Téléphone</th>
                            <th>Email</th>
                            <th>Acceptation</th>
														<th>Suppressions</th>
                          </thead>
                          <tbody>
                            <?php if(count($newsUser)) : ?>

                            <?php foreach($newsUser as $user) : ?>

                              <tr>
                                <form action="actions/Acceptation.php?code=<?= $user->getCIN() ?>" method="POST">
                                <td><?= $user->getCIN() ?></td>
                                <td><?= $user->getNom() ?></td>
                                <td><?= $user->getPrenom() ?></td>
                                <td><?= $user->getDescription() ?></td>
                                <td><?= $user->getTelephone() ?></td>
                                <td><?= $user->getEmail() ?></td>
                                <td>
                                      <input name="Demande_accepté" type="submit"  class="BTNEN ICR" value="Accepter" onclick="return confirm('Etes-vous sur d\'accepter cette demande?')">
                                </td>
																<td>
																			<input name="Del_demande" type="submit"  class="BTNEN ICR del" value="Supprimer" onclick="return confirm('Etes-vous sur de supprimer cette demande?')" >
																</td>
                                </form>
                              </tr>
                            <?php endforeach; ?>

                          <?php else : echo "<tr><td colspan='8'>Aucun demandes a été trouver</td></tr>"; endif; ?>
                          </tbody>
                      </table>
										</div>
								  </div>

              </div>
              <div id="users-action" style="display:none;" >
                  <div class="title">
                    La gestion des utilisateurs
                  </div>
                  <div class="row">

										<center>	<a class="BTNEN ICR" id="ADD" >ADD USER</a> </center>
										<div class="idscrol">


											<table class='table' >
													<thead>
														<th>CIN</th>
														<th>Nom</th>
														<th>Prenom</th>
														<th>Description</th>
														<th>Téléphone</th>
														<th>Email</th>
														<th>Modifications</th>
														<th>Suppressions</th>
													</thead>
													<tbody>

														<?php if(count($afficher_user)) : ?>

														<?php for($i=0;$i<count($afficher_user);$i++){ ?>
															<form action="actions/Edit_delUser.php?code=<?=$afficher_user[$i]->getCIN() ?>" method="POST">
															<tr>
																<td><?= $afficher_user[$i]->getCIN() ?></td>
																<td><?= $afficher_user[$i]->getNom() ?></td>
																<td><?= $afficher_user[$i]->getPrenom() ?></td>
																<td><?= $afficher_user[$i]->getDescription() ?></td>
																<td><?= $afficher_user[$i]->getTelephone() ?></td>
																<td><?= $afficher_user[$i]->getEmail() ?></td>
																<td>
																			<input name="Edit_user" type="button"  class="BTNEN ICR" value="Modifier" onclick="document.getElementById('modifier<?=$i?>').style.display='flex';document.getElementById('ombre').style.display='flex';"  >

																</td>
																<td>
																			<input name="Del_user" type="submit"  class="BTNEN ICR del" value="Supprimer" onclick="return confirm('Etes-vous sur de supprimer cet utilisateur ?')" >
																</td>
															</tr>

															<!-- popUP EDIT USER -->
															<div id="modifier<?=$i?>" class="modifier">
																<div class="modal-contents">

																	<div class="close" onclick="document.getElementById('modifier<?=$i?>').style.display='none';document.getElementById('ombre').style.display='none';">+</div>
																	<!-- EDIT USER  -->
																	<?php
																			include("includes/edit_user.php");
																		?>

																</div>
															</div>
															</form>

														<?php } ?>

													<?php else : echo "<tr><td colspan='8'>Aucun utilisateur a été trouver</td></tr>"; endif; ?>

																</tbody>
														</table>

										</div>
                  </div>
              </div>
							<div id="rdv-action"  style="display:none;" >
								<div class="title">	Gestion de Rendez-vous </div>
								<center>

											 <div class="select-box">
												 <div class="options-container">

													 <?php if(count($afficher_medecin)) : ?>

													 <?php foreach($afficher_medecin as $user) : ?>
														 <form action="home.php?id=<?php echo $user->getNuMedecin(); ?>" method="POST" class="row">

														 <div >
															 <input type="submit" class="radio" name="<?= $user->getNuMedecin() ?>" value="<?php echo $user->getNom()." ".$user->getPrenom()." :: ".$user->getSpecialite(); ?>" onclick="submit"/>
														 </div>

													 </form>

													 <?php endforeach; ?>
												 <?php else : echo '<div class="option"><input type="radio" class="radio" id="Vide" name="Vide"/><label for="Vide">Aucun Médecin a été trouver</label></div>'; endif; ?>

												 </div>

												 <div class="selected">
													 <?php if(!empty($_GET['id'])): ?>
														 <?php echo User::findBy(User::findBy($_GET['id'],'Nu_medecin','Medecin')->getCIN())->getNom()." ".User::findBy(User::findBy($_GET['id'],'Nu_medecin','Medecin')->getCIN())->getPrenom()." :: ".User::findBy($_GET['id'],'Nu_medecin','Medecin')->getSpecialite();  ?>
													 <?php else : echo "Choisir Le Medecin et la spécialité"; endif; ?>

												 </div>
											 </div>
											 <div class="idscrol">
											 <table class='table'>
													 <thead>
														 <th>Numéro de médecin</th>
														 <th>CIN du Patient</th>
														 <th>Date de Rendez-vous</th>
														 <th>Modifier</th>
														 <th>Supprimer</th>

													 </thead>
													 <tbody>
														 <?php if(!empty($_GET['id'])): ?>
														 <?php if(count($afficher_rdv)) : ?>
														 <?php for($i=0;$i<count($afficher_rdv);$i++) : ?>

															 <tr>
																 <form action="actions/Edit_delRDV.php?idrdv=<?= $afficher_rdv[$i]->getIdRDV() ?>" method="POST">

																 <td><?= $afficher_rdv[$i]->getNuMedecin() ?></td>
																 <td><?= $afficher_rdv[$i]->getCINP() ?></td>
																 <td><?= $afficher_rdv[$i]->getDateRdv() ?></td>
																 <td>
																			 <input name="popup_rdv" type="button"  class="BTNEN ICR" value="Modifier" onclick="document.getElementById('edit_rdv<?=$i?>').style.display='flex';document.getElementById('ombre').style.display='flex';" >

																 </td>
																 <td>
																			 <input name="del_rdv" type="submit"  class="BTNEN ICR del" value="Annuler" onclick="return confirm('Etes-vous sur d\'annuler ce Rendez-vous ?')">

																 </td>

																 <!-- popUP EDIT RDV -->
																 <div id="edit_rdv<?=$i?>" class="modifier">
																	 <div class="modal-contents" style="width: 35.2em;">

																		 <div class="close" onclick="document.getElementById('edit_rdv<?=$i?>').style.display='none';document.getElementById('ombre').style.display='none';">+</div>
																		 <!-- appeler la formulaire edit_rdv -->
																		 <?php
																				 include("includes/edit_rdv.php");
																			 ?>

																	 </div>
																 </div>

																 </form>
															 </tr>
														 <?php endfor; ?>
													 <?php else : echo "<tr><td colspan='5'>Aucune Rendez-vous a été trouver</td></tr>"; endif; ?>
													 <?php else : echo "<tr><td colspan='5'>Vous devez choisir le medecin</td></tr>"; endif; ?>
													 </tbody>
											 </table>
										 	</div>
								</center>
							</div>
							<div id="rec-action"  style="display:none;" >
								<div class="title">	Gestion de réclamations </div>
								<div class="row">
									<div class="idscrol">
										<table class='table'>
											<thead>
												<th>N°reclamation</th>
												<th>Date de reclamation</th>
												<th>Contenu</th>
												<th>Capture CIN</th>
												<th>Corriger</th>
												<th>Rejeter</th>

											</thead>
											<tbody>
												<?php if(count($afficher_reclamation)) : ?>

												<?php for($i=0;$i<count($afficher_reclamation);$i++) : ?>

													<tr>
														<form action="actions/ACT_corriger_reclamation.php?NuRecl=<?=$afficher_reclamation[$i]->getNuRecl() ?>" method="POST">

														<td><?= $afficher_reclamation[$i]->getNuRecl() ?></td>
														<td><?= $afficher_reclamation[$i]->getDateRecl() ?></td>
														<td><?= $afficher_reclamation[$i]->getContenu() ?></td>
														<td> <?php echo "<img src='data:image;base64,".base64_encode($afficher_reclamation[$i]->getCaptureCIN())."' alt='Capture CIN' class='pic'>"; ?> </td>
														<td>
																	<input name="Correct_user" type="button"  class="BTNEN ICR" value="Corriger" onclick="document.getElementById('Corriger<?=$i?>').style.display='flex';document.getElementById('ombre').style.display='flex';"  >

														</td>
														<td>
																		<input name="del_recl" type="submit"  class="BTNEN ICR del" value="Rejeter" onclick="return confirm('Etes-vous sur de rejeter cette réclamation ?')">

														</td>
														<!-- popUP corriger Reclamation -->
														<div id="Corriger<?=$i?>" class="modifier">
															<div class="modal-contents">

																<div class="close" onclick="document.getElementById('Corriger<?=$i?>').style.display='none';document.getElementById('ombre').style.display='none';">+</div>
																<!-- Corrige les informations d'utilisateurs -->
																<?php
																		include("includes/Corriger_reclamation.php");
																	?>

															</div>
														</div>
														</form>
													</tr>
												<?php endfor; ?>

											<?php else : echo "<tr><td colspan='6'>Aucune Reclamation a été trouver</td></tr>"; endif; ?>
											</tbody>
									</table>
									</div>
								</div>
							</div>
							<div id="account-action"  style="display:none;" >
								<div class="title">	My account </div>
								<div class="row">
									<!-- EDIT My EMAIL/ Password  -->
									<?php
											include("includes/modifierMyInfo.php");
										?>
								</div>
							</div>
					  </div>
        </main>
    </div>
		<!-- popUP ADD USER -->
	<div id="ajouter">
		<div class="modal-contents">

			<div class="close" id="closeADD">+</div>
			<!-- ADD NEW USER  -->
			<?php include("includes/add_user.php"); 	?>

		</div>
	</div>
	<!-- Ombre for popUp edit -->
	<div id="ombre"></div>

	<script>
		let ad=document.getElementById("ADD");
		let clAD=document.getElementById("closeADD");
		ad.onclick = function(){
			document.getElementById("ajouter").style.display="flex";
		};

		clAD.onclick = function(){
			document.getElementById("ajouter").style.display="none";
		};

	</script>
	<!-- CE SCRIPT concerne Input dans la page login -->
    <script type="text/javascript" src="js/admin.js"></script>

  </body>
</html>
