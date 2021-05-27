<?php

    class RDV extends User {

        //ATTRIBUTES
        protected $idRDV;
        protected $CINP;
        protected $NuMedecin;
        protected $dateRdv;
        protected $Etat;


        //CONSTRUCTORS

        public function __construct()
        {
            parent::__construct();
            $this->idRDV = 0;
            $this->CINP = null;
            $this->NuMedecin = null;
            $this->dateRdv = null;
            $this->Etat = null;

        }

        //SETTERS
        public function setIdRDV($idRDV) : void { $this->idRDV = $idRDV; }
        public function setCINP($CINP) : void { $this->CINP = $CINP; }
        public function setNuMedecin($NuMedecin) : void { $this->NuMedecin = $NuMedecin; }
        public function setDateRdv($dateRdv) : void { $this->dateRdv = $dateRdv; }
        public function setEtat($Etat) : void { $this->Etat = $Etat; }


        //GETTERS
        public function getIdRDV() : int { return $this->idRDV; }
        public function getCINP() : string { return $this->CINP; }
        public function getNuMedecin() : string { return $this->NuMedecin; }
        public function getDateRdv() : string { return $this->dateRdv; }
        public function getEtat() : string { return $this->Etat; }

        //Get all rdv
        public static function findAll($table_name = 'rdv') {
            $data = Model::findAll($table_name);
            if($data != null) {

                $recls = array(count($data));

                for($i = 0; $i < count($data); $i++) {

                    $info = $data[$i];
                    $rdv = new RDV();
                    $rdv->setIdRDV($info['id_rdv']);
                    $rdv->setCINP($info['CIN']);
                    $rdv->setNuMedecin($info['Nu_medecin']);
                    $rdv->setDateRdv($info['date_rdv']);
                    $rdv->setEtat($info['Etat']);

                    $rdvs[$i] = $rdv;
                }

                return $rdvs;

            } else {
                return null;
            }
        }


                //Get RDV By id
                public static function findBy($value, $column = 'id_rdv', $table_name = 'rdv') {
                    $data = Model::findBy($value, $column, $table_name);
                      if($data != null){
                        $rdv = new RDV();
                        $rdv->setIdRDV($data['id_rdv']);
                        $rdv->setCINP($data['CIN']);
                        $rdv->setNuMedecin($data['Nu_medecin']);
                        $rdv->setDateRdv($data['date_rdv']);
                        $rdv->setEtat($data['Etat']);
                        return $rdv;
                      }else {
                        return null;
                      }


                  }


                        //Delete rdv
                       public static function Anurdv($rdv) : bool {
                         $query = "update rdv set Etat= ? where id_rdv = ?";
                         $params = [
                            $rdv->getEtat(),
                             $rdv->getIdRDV()
                         ];
                         $AnuToRdv = Model::submitData($query, $params);
                         return $AnuToRdv;

                       }
                       //Update RDV
                      public static function updateRDV($rdv) : bool {
                        //UPDATE DATE_rdv  from rdv table
                        $query = "update rdv set date_rdv= ? , Etat= ? where id_rdv = ?";
                        $params = [
                           $rdv->getDateRdv(),
                           $rdv->getEtat(),
                            $rdv->getIdRDV()
                        ];
                        $UPDRDV = parent::submitData($query, $params);

                         return $UPDRDV;
                      }

                      //Get RDV By id
                      public static function findRDV($NuMedecin,$value, $column = 'id_rdv', $table_name = 'rdv') {
                          $data = Model::findBy($value, $column, $table_name);
                            if(($data != null) && ($data['Nu_medecin']==$NuMedecin)){
                              $rdv = new RDV();
                              $rdv->setIdRDV($data['id_rdv']);
                              $rdv->setCINP($data['CIN']);
                              $rdv->setNuMedecin($data['Nu_medecin']);
                              $rdv->setDateRdv($data['date_rdv']);
                              $rdv->setEtat($data['Etat']);
                              return $rdv;
                            }else {
                              return null;
                            }


                        }

                        //Send email

                        //Send mail RDV //
                     public static function sendEmail($user,$RDV) : bool {

                         $user = parent::findBy($user->getEmail(), 'email');

                         $to = $user->getEmail();
                          $from = "<CHUO@newsletter.com>";
                         $subject = 'CHUO NEWSLETTER : Reclamation';
                         $message = '
                         <html>
                            <head>
                               <style>
                                  .banner-color {
                                  background-color: #eb681f;
                                  }
                                  .title-color {
                                  color: #0066cc;
                                  }
                                  .button-color {
                                  background-color: #0066cc;
                                  }
                                  @media screen and (min-width: 500px) {
                                  .banner-color {
                                  background-color: #0066cc;
                                  }
                                  .title-color {
                                  color: #eb681f;
                                  }
                                  .button-color {
                                  background-color: #eb681f;
                                  }
                                  }
                               </style>
                            </head>
                            <body>
                               <div style="background-color:#ececec;padding:0;margin:0 auto;font-weight:200;width:100%!important">
                                  <table align="center" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed;font-weight:200;font-family:Helvetica,Arial,sans-serif" width="100%">
                                     <tbody>
                                        <tr>
                                           <td align="center">
                                              <center style="width:100%">
                                                 <table bgcolor="#FFFFFF" border="0" cellspacing="0" cellpadding="0" style="margin:0 auto;max-width:512px;font-weight:200;width:inherit;font-family:Helvetica,Arial,sans-serif" width="512">
                                                    <tbody>
                                                       <tr>
                                                          <td bgcolor="#F3F3F3" width="100%" style="background-color:#f3f3f3;padding:12px;border-bottom:1px solid #ececec">
                                                             <table border="0" cellspacing="0" cellpadding="0" style="font-weight:200;width:100%!important;font-family:Helvetica,Arial,sans-serif;min-width:100%!important" width="100%">
                                                                <tbody>
                                                                   <tr>
                                                                      <td valign="middle" width="50%" align="right" style="padding:0 0 0 10px"><span style="margin:0;color:#4c4c4c;white-space:normal;display:inline-block;text-decoration:none;font-size:12px;line-height:20px">'.date("Y-m-d").'</span></td>
                                                                      <td width="1">&nbsp;</td>
                                                                   </tr>
                                                                </tbody>
                                                             </table>
                                                          </td>
                                                       </tr>
                                                       <tr>
                                                          <td align="left">
                                                             <table border="0" cellspacing="0" cellpadding="0" style="font-weight:200;font-family:Helvetica,Arial,sans-serif" width="100%">
                                                                <tbody>
                                                                   <tr>
                                                                      <td width="100%">
                                                                         <table border="0" cellspacing="0" cellpadding="0" style="font-weight:200;font-family:Helvetica,Arial,sans-serif" width="100%">
                                                                            <tbody>
                                                                               <tr>
                                                                                  <td align="center" bgcolor="#8BC34A" style="padding:20px 48px;color:#ffffff" class="banner-color">
                                                                                     <table border="0" cellspacing="0" cellpadding="0" style="font-weight:200;font-family:Helvetica,Arial,sans-serif" width="100%">
                                                                                        <tbody>
                                                                                           <tr>
                                                                                              <td align="center" width="100%">
                                                                                               <img src="../img/logo-accueil.png" alt="Centre Hospitalier Universitaire Mohammed VI OUJDA">
                                                                                              </td>
                                                                                           </tr>
                                                                                        </tbody>
                                                                                     </table>
                                                                                  </td>
                                                                               </tr>
                                                                               <tr>
                                                                                  <td align="center" style="padding:20px 0 10px 0">
                                                                                     <table border="0" cellspacing="0" cellpadding="0" style="font-weight:200;font-family:Helvetica,Arial,sans-serif" width="100%">
                                                                                        <tbody>
                                                                                           <tr>
                                                                                              <td align="center" width="100%" style="padding: 0 15px;text-align: justify;color: rgb(76, 76, 76);font-size: 12px;line-height: 18px;">
                                                                                                 <h3 style="font-weight: 600; padding: 0px; margin: 0px; font-size: 16px; line-height: 24px; text-align: center;" class="title-color">Bonjour '.$user->getNom().',</h3>
                                                                                                 <p style="margin: 20px 0 30px 0;font-size: 15px;text-align: center;">L\'Etat du votre Rendez-vous que vous avez reservé pour '.$RDV->getDateRdv().' a été changer!</p>
                                                                                                 <div style="font-weight: 200; text-align: center; margin: 25px;"><a style="padding:0.6em 1em;border-radius:600px;color:#ffffff;font-size:14px;text-decoration:none;font-weight:bold" class="button-color">Consulter votre compte pour plus de détails</a></div>
                                                                                              </td>
                                                                                           </tr>
                                                                                        </tbody>
                                                                                     </table>
                                                                                  </td>
                                                                               </tr>
                                                                               <tr>
                                                                               </tr>
                                                                               <tr>
                                                                               </tr>
                                                                            </tbody>
                                                                         </table>
                                                                      </td>
                                                                   </tr>
                                                                </tbody>
                                                             </table>
                                                          </td>
                                                       </tr>
                                                       <tr>
                                                          <td align="left">
                                                             <table bgcolor="#FFFFFF" border="0" cellspacing="0" cellpadding="0" style="padding:0 24px;color:#999999;font-weight:200;font-family:Helvetica,Arial,sans-serif" width="100%">
                                                                <tbody>
                                                                   <tr>
                                                                      <td align="center" width="100%">
                                                                         <table border="0" cellspacing="0" cellpadding="0" style="font-weight:200;font-family:Helvetica,Arial,sans-serif" width="100%">
                                                                            <tbody>
                                                                               <tr>
                                                                                  <td align="center" valign="middle" width="100%" style="border-top:1px solid #d9d9d9;padding:12px 0px 20px 0px;text-align:center;color:#4c4c4c;font-weight:200;font-size:12px;line-height:18px">Regards,
                                                                                     <br><b>The CHUO Team</b>
                                                                                  </td>
                                                                               </tr>
                                                                            </tbody>
                                                                         </table>
                                                                      </td>
                                                                   </tr>
                                                                   <tr>
                                                                      <td align="center" width="100%">
                                                                         <table border="0" cellspacing="0" cellpadding="0" style="font-weight:200;font-family:Helvetica,Arial,sans-serif" width="100%">
                                                                            <tbody>
                                                                               <tr>
                                                                                  <td align="center" style="padding:0 0 8px 0" width="100%"></td>
                                                                               </tr>
                                                                            </tbody>
                                                                         </table>
                                                                      </td>
                                                                   </tr>
                                                                </tbody>
                                                             </table>
                                                          </td>
                                                       </tr>
                                                    </tbody>
                                                 </table>
                                              </center>
                                           </td>
                                        </tr>
                                     </tbody>
                                  </table>
                               </div>
                            </body>
                         </html>';

                         //from w3schools
                         $headers = 'From: ' . $from . "\r\n".
                            "MIME-Version: 1.0" . "\r\n" .
                            "Content-type: text/html; charset=UTF-8" . "\r\n";

                         if(mail($to, $subject, $message, $headers))
                             //Email Sended
                             return true;
                         else
                             return false;

                     }


    }

?>
