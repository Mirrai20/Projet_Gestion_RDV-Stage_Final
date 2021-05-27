<?php

spl_autoload_register(function ($class_name) {
		include $class_name . '.php';
});

	$response = array();

	if(isset($_GET['filcall'])){

		switch($_GET['filcall']){

			case 'updateProfile':
			//Récupération des valeurs entrant
			$CIN = $_POST['CIN'];
			$email = $_POST['email'];
			$tele = $_POST['telephone'];
			$password = $_POST['password'];
			$dt = Model::findBy($CIN,'CIN','user');

			if($password == $dt['Password']){
				$data = Model::findBy($email,'Email','user');
				if(!($data != null && $data['Email']!=$email)) {
								$dataV = Model::findBy($tele,'telephone','user');
									if(!($dataV != null && $dataV['telephone']!=$tele)){
										if(!empty($_POST['newPassword'])){
											$newPassword = $_POST['newPassword'];
													$query = "update user set Email = ?, telephone = ?, Password = ? where CIN = ?";
													$params = [	$email,$tele,$newPassword,$CIN];
													$UPDToUser = Model::submitData($query, $params);
													$user = array(
														'cin'=>$CIN,
														'email'=>$email,
														'telephone'=>$tele,
														'password'=>$newPassword
													);
													if($UPDToUser){
														$response['error'] = false;
														$response['message'] = 'Vos informations ont été bien modifier !';
														$response['user'] = $user;
													}else{
														$response['error'] = true;
														$response['message'] = 'Server Issues!';
													}
										}else {
														$query = "update user set Email = ?, telephone = ? where CIN = ?";
														$params = [	$email,$tele,$CIN];
														$UPDToUser = Model::submitData($query, $params);
														$user = array(
															'cin'=>$CIN,
															'email'=>$email,
															'telephone'=>$tele,
															'password'=>$password
														);
														if($UPDToUser){
															$response['error'] = false;
															$response['message'] = 'Vos informations ont été bien modifier !';
															$response['user'] = $user;
														}else{
															$response['error'] = true;
															$response['message'] = 'Server Issues!';
														}
										}

										}else{
											//Number phone already used by another account
											$response['error'] = true;
											$response['message'] = 'Numéro de téléphone est déjà utilisé par un autre compte!';
										}


				} else {

						//Email already used by another account
						$response['error'] = true;
						$response['message'] = 'Cet adress Email est déjà utilisé par un autre compte!';


				}

			}else{
				//Email already used by another account
				$response['error'] = true;
				$response['message'] = 'Le mot de passe que vous avez tapé est incorrect!';
			}
			break;

			case 'signup':
			//Récupération des valeurs entrant
			$CIN = $_POST['CIN'];
			$nom = $_POST['nom'];
			$prenom = $_POST['prenom'];
			$dateN = $_POST['dateNaissance'];
			$tele = $_POST['telephone'];
			$email = $_POST['email'];
			$password = $_POST['password'];
			$description= $_POST['description'];
			$acceptation=0;
			if($description=="Medecin"){
				$specialite = $_POST['specialite'];
				$NuMedecin = $_POST['Numedecin'];
				$conge="Non";
			}else {
				$sex  = $_POST['sex'];
			}

			if(Model::findBy($email, 'email','user') == null) {
						if(Model::findBy($CIN, 'CIN','user') == null){
									if(Model::findBy($tele,'telephone','user') == null){
										if($description=="Medecin"){
											if(Model::findBy($NuMedecin, 'Nu_medecin','Medecin') != null){
												//Number medecin already used by another account
												$response['error'] = true;
												$response['message'] = 'Number medecin already used by another account !';
											}else{
												$medecin = array(
													'cin'=>$CIN,
													'nom'=>$nom,
													'prenom'=>$prenom,
													'email'=>$email,
													'password'=>$password,
													'telephone'=>$tele,
													'dateN'=>$dateN,
													'description'=>$description,
													'numedecin'=>$NuMedecin,
													'specialite'=>$specialite
												);
													$query = 'insert into user (CIN,Nom,Prenom,Date_naissance,telephone,Email,Password,acceptation,description) values (?, ?, ?, ?, ?, ?, ?, ?, ?)';
													$params = [$CIN,$nom,$prenom,$dateN,$tele,$email,$password,$acceptation,$description];
													$addToUser = Model::submitData($query, $params);
													//Insert user in Medecin table
													$query ='insert into Medecin (Nu_medecin,CIN,specialite,en_conge) values (?,?,?,?)';
													$params = [$NuMedecin,$CIN,$specialite,$conge];
													$addToObject = Model::submitData($query, $params);
													//Insert Medecin in table spécialité
													$query ='insert into specialite (Nu_medecin,specialite) values (?,?)';
													$params =	[$NuMedecin,$specialite];
													$addToObjectSP = Model::submitData($query, $params);

													if($addToUser && $addToObject && $addToObjectSP){
														$response['error'] = false;
														$response['message'] = 'User registered successfully';
														$response['user'] = $medecin;
													}else{
														$response['error'] = true;
														$response['message'] = 'Server Issues!';
													}
											}

										}else{
											if($description=="Patient"){
												$patient = array(
													'cin'=>$CIN,
												 'nom'=>$nom,
												 'prenom'=>$prenom,
												 'email'=>$email,
												 'password'=>$password,
												 'telephone'=>$tele,
												 'dateN'=>$dateN,
												 'description'=>$description,
													'gender'=>$sex
												);
												$query = 'insert into user (CIN,Nom,Prenom,Date_naissance,telephone,Email,Password,acceptation,description) values (?, ?, ?, ?, ?, ?, ?, ?, ?)';
												$params = [$CIN,$nom,$prenom,$dateN,$tele,$email,$password,$acceptation,$description];
												$addToUser = Model::submitData($query, $params);
												//Insert user in Patient
													$addToObject = Model::submitData(
															'insert into Patient (CIN,sex) values (?,?)',
															[$CIN,$sex]
													);
													if($addToUser && $addToObject ){
														$response['error'] = false;
														$response['message'] = 'User registered successfully';
														$response['user'] = $patient;
													}else{
														$response['error'] = true;
														$response['message'] = 'Server Issues!';
													}
											}
										}


									}else{
										//Number phone already used by another account
										$response['error'] = true;
										$response['message'] = 'Numéro de téléphone est déjà utilisé par un autre compte!';
									}
							}else {
								//CIN already used by another account
								$response['error'] = true;
								$response['message'] = 'Ce numéro du CIN est déjà utilisé par un autre compte!';

							}

			} else {

					//Email already used by another account
					$response['error'] = true;
					$response['message'] = 'Cet adress Email est déjà utilisé par un autre compte!';


			}
			break;

			case 'login':
			if(!isset($_POST['email']) && !isset($_POST['password'])) {
				$response['error'] = true;
				$response['message'] = 'Vous devez taper votre email & password';
			}

			$email = $_POST['email'];
			$password = $_POST['password'];

			if(($data = Model::findBy($email, 'email','user')) != null) {

        if($password == $data['Password']) {
          if($data['acceptation']==1) {
            if($data['description']=="Patient"){
							$dt = Model::findBy($data['CIN'], 'CIN',$data['description']);
							$patient = array(
	              'cin'=>$data['CIN'],
	              'nom'=>$data['Nom'],
	              'prenom'=>$data['Prenom'],
								'dateN'=>$data['Date_naissance'],
								'telephone'=>$data['telephone'],
								'email'=>$data['Email'],
								'password'=>$data['Password'],
								'description'=>$data['description'],
								'gender'=>$dt['sex']
	            );
							$response['error'] = false;
	            $response['message'] = 'Login successfull';
	            $response['user'] = $patient;
						}else{
							if($data['description']=="Medecin"){
								$dt = Model::findBy($data['CIN'], 'CIN',$data['description']);
								$medecin = array(
		              'cin'=>$data['CIN'],
		              'nom'=>$data['Nom'],
		              'prenom'=>$data['Prenom'],
									'email'=>$email,
									'password'=>$password,
									'telephone'=>$data['telephone'],
									'dateN'=>$data['Date_naissance'],
									'description'=>$data['description'],
									'numedecin'=>$dt['Nu_medecin'],
									'specialite'=>$dt['specialite']
		            );
								$response['error'] = false;
								$response['message'] = 'Login successfull';
								$response['user'] = $medecin;
							}else{
								$response['error'] = true;
								$response['message'] = 'L\'administrateur doit se connecter par l\'application web !';

							}
						}


          } else {

            // cet utilisateur n'a pas encore été accepter
            $response['error'] = true;
            $response['message'] = 'Votre demande d\'inscription n\'a pas encore été accepter !';

          }

        } else {

          //wrong password
          $response['error'] = true;
          $response['message'] = 'Invalid Email or password';

        }

			} else {

					//Email not found
					$response['error'] = true;
					$response['message'] = 'Invalid Email or password';

			}

			break;
			case 'SP':
			$data = Model::findAllSP('specialite');
			if( $data != null) {
							//	$sp = array();
							//	array_push($cart, 13);
							$sp = array();
							for($i = 0; $i < count($data); $i++) {
										$info = $data[$i];
										 array_push($sp,array('specialite'=>$info['specialite'],'Medecin'=>'null'));
							}
							/*	$sp = array(
												array('specialite'=>$data['specialite'],'Medecin'=>$data['Nu_medecin']),
												array('specialite'=>$data['specialite']."1",'Medecin'=>$data['Nu_medecin'])
									);*/
							/*	$medecin = array(
									'cin'=>$data['CIN'],
									'nom'=>$data['Nom'],
									'prenom'=>$data['Prenom'],
									'email'=>$email,
									'password'=>$password,
									'telephone'=>$data['telephone'],
									'dateN'=>$data['Date_naissance'],
									'description'=>$data['description'],
									'numedecin'=>$dt['Nu_medecin'],
									'specialite'=>$dt['specialite']
								);*/
								$response['error'] = false;
								$response['message'] = 'Voici tous les spécialités !';
								$response['specialite'] = $sp;
			} else {

					//specialite not found
					$response['error'] = true;
					$response['message'] = 'Aucun spécialité a été trouver!';

			}

			break;

			case 'OLDRDV':
		//	$_POST["CIN"]="fc123456";
			if(!empty($_POST["CIN"])){
				$data = Model::findAllRDV('rdv');
			}

			if( $data != null) {

							$date = date('Y-m-d H:i:s');
							$CIN = $_POST["CIN"];
							$oldrdv = array();
							for($i = 0; $i < count($data); $i++) {
										$info = $data[$i];
										if($CIN==$info['CIN'] && $info['date_rdv']<$date){
												$sp = Model::findBy($info['Nu_medecin'], 'Nu_medecin','specialite');
												$cinMedecin= Model::findBy($info['Nu_medecin'], 'Nu_medecin','medecin');
												$name = Model::findBy($cinMedecin["CIN"], 'CIN','user');
											 	array_push($oldrdv,array('daterdv'=>$info['date_rdv'],'specialite'=>$sp['specialite'],'medecin'=>"Doc ".$name["Nom"]));
										}
							}

								$response['error'] = false;
								$response['message'] = 'Voici votre historique des anciens RDV !';
								$response['OLDRDV'] = $oldrdv;
			} else {

					//specialite not found
					$response['error'] = true;
					$response['message'] = 'Aucun ancien RDV a été trouver!';

			}

			break;

			case 'UplFILE':

			if( !empty($_POST["CIN"]) && !empty($_POST["Contenu"]) && !empty($_POST["image"])) {
							//	$target = "images/".basename($_POST["image"]);
								$CIN=$_POST["CIN"];
								$Contenu=$_POST["Contenu"];
								$Statut="Encoure";
								$image = base64_decode($_POST['image']);

								$query ='insert into reclamation (CIN,Capture_CIN,Contenu,Statut) values (?,?,?,?)';
								$params =	[$CIN,$image,$Contenu,$Statut];
								$addtoRecl = Model::submitData($query, $params);

								if($addtoRecl){
									$response['error'] = false;
									$response['message'] = 'Votre reclamation a été bien envoyer !';
									//	$response['UplFILE'] = $oldrdv;

								}else{
									$response['error'] = true;
									$response['message'] = 'Server Issues!';
								}




			} else {

					//specialite not found
					$response['error'] = true;
					$response['message'] = 'Essayer de renvoyer ton reclamation!';

			}

			break;

			case 'ALLrecl':
		//	$_POST["CIN"]="fc123456";
			if(!empty($_POST["CIN"])){
				$data = Model::findAllRecl('reclamation');
			}

			if( $data != null) {

							$CIN = $_POST["CIN"];
							$ALLrecl = array();
							for($i = 0; $i < count($data); $i++) {
										$info = $data[$i];
										if($CIN==$info['CIN']){
											 	array_push($ALLrecl,array('dateRecl'=>$info['date_reclamation'],'contenu'=>$info['Contenu'],'statut'=>$info["Statut"]));
										}
							}

								$response['error'] = false;
								$response['message'] = 'Voici vos reclamations !';
								$response['ALLrecl'] = $ALLrecl;
			} else {

					//specialite not found
					$response['error'] = true;
					$response['message'] = 'Aucun reclamation a été trouver!';

			}

			break;

			case 'ALLmedc':
			//$_POST["specialite"]="Angiologue";
			if(!empty($_POST["specialite"])){
				$data = Model::findAll('medecin');
			}

			if( $data != null) {

							$specialite = $_POST["specialite"];
							$ALLmd = array();
							for($i = 0; $i < count($data); $i++) {
										$info = $data[$i];
										if(($specialite==strtoupper($info['specialite'])) && ($info['en_conge'] =="Non") ){
											$info2 = Model::findBy($info['CIN'],'CIN','user');
												array_push($ALLmd,array('Nom'=>$info2['Nom'],'Prenom'=>$info2['Prenom'],'NuMedecin'=>$info['Nu_medecin']));
										}
							}

								$response['error'] = false;
								$response['message'] = 'Voici les medecins disponible dans la spécialité '.$specialite." !";
								$response['ALLmedc'] = $ALLmd;
			} else {

					//specialite not found
					$response['error'] = true;
					$response['message'] = 'Aucun Medecin a été trouver dans la spécialité '.$specialite." !";

			}

			break;

			case 'PrRDV':
			if((!empty($_POST["CIN"])) && (!empty($_POST["Numedecin"])) && (!empty($_POST["dateRDV"]))){
				$data = Model::findBy($_POST["dateRDV"],'date_rdv','RDV');

			}

			if( ($data == null) || ($data["Nu_medecin"] != $_POST["Numedecin"])) {

							$CIN = $_POST["CIN"];
							$medecin = $_POST["Numedecin"];
							$dateRDV = $_POST["dateRDV"];
							$Etat="Valider";

							$query ='insert into rdv (Nu_medecin,CIN,date_rdv,Etat) values (?,?,?,?)';
							$params =	[$medecin,$CIN,$dateRDV,$Etat];
							$addtoRDV = Model::submitData($query, $params);

							if($addtoRDV){
								$response['error'] = false;
								$response['message'] = 'La date du Rendez-vous a été bien reservé!';
								//	$response['UplFILE'] = $oldrdv;

							}else{
								$response['error'] = true;
								$response['message'] = 'Server Issues! ';
							}
			} else {

					//RDV est déja pris par un autre patient
					$response['error'] = true;
					$response['message'] = "La date du Rendez-vous que vous avez choisi a été déja reserver par un autre patient !";

			}

			break;

			case 'ConsuRDV':
		//	$_POST["CIN"]="fc123456";
			if(!empty($_POST["CIN"])){
				$data = Model::findAllRDV('rdv');
			}

			if( $data != null) {

							$date = date('Y-m-d');
							$CIN = $_POST["CIN"];
							$Conurdv = array();
							for($i = 0; $i < count($data); $i++) {
										$info = $data[$i];
										if($CIN==$info['CIN']){
												$sp = Model::findBy($info['Nu_medecin'], 'Nu_medecin','specialite');
												$cinMedecin= Model::findBy($info['Nu_medecin'], 'Nu_medecin','medecin');
												$name = Model::findBy($cinMedecin["CIN"], 'CIN','user');
												$daterdv = explode(" ", $info['date_rdv']);
												if($daterdv[0]>$date){
											 	array_push($Conurdv,array('daterdv'=>$info['date_rdv'],'specialite'=>$sp['specialite'],'medecin'=>$name["Nom"],'Etat'=>$info["Etat"]));
												}
										}
							}
							if(count($Conurdv)==0){
								array_push($Conurdv,array('daterdv'=>'Aucun','specialite'=>'Aucun','medecin'=>'Aucun','Etat'=>'Aucun'));
							}

								$response['error'] = false;
								$response['message'] = 'Voici vos Rendez-vous Encoure !';
								$response['ConsuRDV'] = $Conurdv;
			} else {

					//specialite not found
					$response['error'] = true;
					$response['message'] = 'Aucun RDV Encoure a été trouver!';

			}

			break;
			case 'RdvPatient':
		//	$_POST["CIN"]="PRR200035";
			if(!empty($_POST["CIN"])){
				$data = Model::findAllRDV('rdv');
			}

			if( $data != null) {


							$date = date('Y-m-d');
							$medecin = Model::findBy($_POST["CIN"], 'CIN','Medecin');

							$NuMedecin = $medecin["Nu_medecin"];
							$AllRdvPatient = array();
							for($i = 0; $i < count($data); $i++) {
										$info = $data[$i];
										if($NuMedecin==$info['Nu_medecin']){
												$name = Model::findBy($info["CIN"], 'CIN','user');
												$sex = Model::findBy($info["CIN"], 'CIN','Patient');
												$daterdv = explode(" ", $info['date_rdv']);
												if($daterdv[0]==$date){
													array_push($AllRdvPatient,array('daterdv'=>$info['date_rdv'],'nom'=>$name["Nom"],'prenom'=>$name["Prenom"],'sex'=>$sex["sex"]));
												}
										}
							}
							if(count($AllRdvPatient)==0){
								array_push($AllRdvPatient,array('daterdv'=>'Aucun','nom'=>'Aucun','prenom'=>'Aucun','sex'=>'Aucun'));
							}
								$response['error'] = false;
								$response['message'] = 'Voici votre liste de Rendez-vous !';
								$response['RdvPatient'] = $AllRdvPatient;
			} else {

					//specialite not found
					$response['error'] = true;
					$response['message'] = 'Aucun RDV a été trouver!';

			}

			break;
			case 'dateValide':
		/*	$_POST["dateValide"]="2020-07-13";
			$_POST["CIN"]="FC54639";*/
			if(!empty($_POST["CIN"]) && !empty($_POST["dateValide"])){
				$data = Model::findAllRDV('rdv');
			}

			if( $data != null) {


							$date = $_POST["dateValide"];
							$medecin = Model::findBy($_POST["CIN"], 'CIN','Medecin');

							$NuMedecin = $medecin["Nu_medecin"];
							$AllRdvPatient = array();
							for($i = 0; $i < count($data); $i++) {
										$info = $data[$i];
										if($NuMedecin==$info['Nu_medecin']){
												$name = Model::findBy($info["CIN"], 'CIN','user');
												$sex = Model::findBy($info["CIN"], 'CIN','Patient');
												$daterdv = explode(" ", $info['date_rdv']);
												if($daterdv[0]==$date){
													array_push($AllRdvPatient,array('daterdv'=>$info['date_rdv'],'nom'=>$name["Nom"],'prenom'=>$name["Prenom"],'sex'=>$sex["sex"]));
												}
										}
							}
							if(count($AllRdvPatient)==0){
								array_push($AllRdvPatient,array('daterdv'=>'Aucun','nom'=>'Aucun','prenom'=>'Aucun','sex'=>'Aucun'));
							}
								$response['error'] = false;
								$response['message'] = 'Voici votre liste de Rendez-vous !';
								$response['RdvPatient'] = $AllRdvPatient;
			} else {

					//specialite not found
					$response['error'] = true;
					$response['message'] = 'Aucun RDV a été trouver!';

			}

			break;

			case 'OLDRdvPatient':
		//	$_POST["CIN"]="PRR200035";
			if(!empty($_POST["CIN"])){
				$data = Model::findAllRDV('rdv');
			}

			if( $data != null) {


							$date = date('Y-m-d',time() - 60 * 60 * 24);
							$medecin = Model::findBy($_POST["CIN"], 'CIN','Medecin');

							$NuMedecin = $medecin["Nu_medecin"];
							$AllRdvPatient = array();
							for($i = 0; $i < count($data); $i++) {
										$info = $data[$i];
										if($NuMedecin==$info['Nu_medecin']){
												$name = Model::findBy($info["CIN"], 'CIN','user');
												$sex = Model::findBy($info["CIN"], 'CIN','Patient');
												$daterdv = explode(" ", $info['date_rdv']);
												if($daterdv[0]==$date){
													array_push($AllRdvPatient,array('daterdv'=>$info['date_rdv'],'nom'=>$name["Nom"],'prenom'=>$name["Prenom"],'sex'=>$sex["sex"]));
												}
										}
							}
							if(count($AllRdvPatient)==0){
								array_push($AllRdvPatient,array('daterdv'=>'Aucun','nom'=>'Aucun','prenom'=>'Aucun','sex'=>'Aucun'));
							}
								$response['error'] = false;
								$response['message'] = 'Voici votre liste de Rendez-vous !';
								$response['RdvPatient'] = $AllRdvPatient;
			} else {

					//specialite not found
					$response['error'] = true;
					$response['message'] = 'Aucun RDV a été trouver!';

			}

			break;
			case 'dateChoisi':
		//	$_POST["CIN"]="fc123456";
			if(!empty($_POST["CIN"]) && !empty($_POST["dateValide"])){
				$data = Model::findAllRDV('rdv');
			}

			if( $data != null) {

							$date = $_POST["dateValide"];
							$CIN = $_POST["CIN"];
							$Conurdv = array();
							for($i = 0; $i < count($data); $i++) {
										$info = $data[$i];
										if($CIN==$info['CIN']){

												$sp = Model::findBy($info['Nu_medecin'], 'Nu_medecin','specialite');
												$cinMedecin= Model::findBy($info['Nu_medecin'], 'Nu_medecin','medecin');
												$name = Model::findBy($cinMedecin["CIN"], 'CIN','user');

												$daterdv = explode(" ", $info['date_rdv']);
												if ($daterdv[0]==$date) {
													array_push($Conurdv,array('daterdv'=>$info['date_rdv'],'specialite'=>$sp['specialite'],'medecin'=>$name["Nom"],'Etat'=>$info["Etat"]));
												}
										}
							}
							if(count($Conurdv)==0){
								array_push($Conurdv,array('daterdv'=>'Aucun','specialite'=>'Aucun','medecin'=>'Aucun','Etat'=>'Aucun'));
							}

								$response['error'] = false;
								$response['message'] = 'Voici vos Rendez-vous Encoure !';
								$response['ConsuRDV'] = $Conurdv;
			} else {

					//specialite not found
					$response['error'] = true;
					$response['message'] = 'Aucun RDV Encoure a été trouver!';

			}

			break;

			default:
				$response['error'] = true;
				$response['message'] = 'Invalid Operation Called';
		}

	}else{
		$response['error'] = true;
		$response['message'] = 'Invalid PHP-Fiel Call';
	}

	echo json_encode($response);
?>
