<?php

    class Reclamation extends User {

        //ATTRIBUTES
        protected $ID;
        protected $NuRecl;
        protected $CaptureCIN;
        protected $contenu;
        protected $dateRecl;
        protected $Statut;


        //CONSTRUCTORS

        public function __construct()
        {
            parent::__construct();
            $this->ID = 0;
            $this->NuRecl = 0;
            $this->CaptureCIN = null;
            $this->contenu = null;
            $this->dateRecl = null;
            $this->Statut = null;

        }

        //SETTERS
        public function setID($ID) : void { $this->ID = $ID; }
        public function setNuRecl($NuRecl) : void { $this->NuRecl = $NuRecl; }
        public function setCaptureCIN($CaptureCIN) : void { $this->CaptureCIN = $CaptureCIN; }
        public function setContenu($contenu) : void { $this->contenu = $contenu; }
        public function setDateRecl($dateRecl) : void { $this->dateRecl = $dateRecl; }
        public function setStatut($Statut) : void { $this->Statut = $Statut; }


        //GETTERS
        public function getID() : string { return $this->ID; }
        public function getNuRecl() : string { return $this->NuRecl; }
        public function getCaptureCIN() : string { return $this->CaptureCIN; }
        public function getContenu() : string { return $this->contenu; }
        public function getDateRecl() : string { return $this->dateRecl; }
        public function getStatut() : string { return $this->Statut; }
        //Get all users
        public static function findAll($table_name = 'reclamation') {
            $data = Model::findAll($table_name);
            if($data != null) {

                $recls = array(count($data));

                for($i = 0; $i < count($data); $i++) {

                    $info = $data[$i];
                    $recl = new Reclamation();
                    $recl->setID($info['CIN']);
                    $recl->setNuRecl($info['Nu_rec']);
                    $recl->setContenu($info['Contenu']);
                    $recl->setCaptureCIN($info['Capture_CIN']);
                    $recl->setDateRecl($info['date_reclamation']);
                    $recl->setStatut($info['Statut']);

                    $recls[$i] = $recl;
                }

                return $recls;

            } else {
                return null;
            }
        }
        //Get Reclamation By id
        public static function findRecl($value, $column = 'Nu_rec', $table_name = 'reclamation') {
            $data = Model::findBy($value, $column, $table_name);
              if($data != null){
                $recl = new Reclamation();
                $recl->setID($data['CIN']);
                $recl->setNuRecl($data['Nu_rec']);
                $recl->setContenu($data['Contenu']);
                $recl->setCaptureCIN($data['Capture_CIN']);
                $recl->setDateRecl($data['date_reclamation']);
                $recl->setStatut($data['Statut']);
                return $recl;
              }else {
                return null;
              }


          }
          //update une reclamation
         public static function UpdRecl($recl) : bool {
           $query = "update reclamation set statut = ? where Nu_rec = ?";
           $params = [
               $recl->getStatut(),
               $recl->getNuRecl()
           ];
           $UpdtoRec = Model::submitData($query, $params);
           return $UpdtoRec;

         }

         //Send email
      public static function sendEmail($user,$recl) : bool {

          $user = parent::findBy($user->getEmail(), 'email');
          $user_CIN = $user->getCIN();

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
                                                                                  <p style="margin: 20px 0 30px 0;font-size: 15px;text-align: center;">Vous avez reçu une réponse a propos la reclamation que vous avez envoyé le '.$recl->getDateRecl().'!</p>
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
