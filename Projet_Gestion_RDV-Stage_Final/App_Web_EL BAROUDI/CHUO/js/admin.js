
  var minimize_btn  = document.getElementById('minimize');
  var maximize_btn = document.getElementById('maximize');
  var leftnav       = document.getElementById("leftnav");
  var toptnav       = document.getElementById("topnav");
  var main          = document.getElementById("main");

  minimize_btn.addEventListener('click',minimize,false);
  maximize_btn.addEventListener('click',maximize,false);

  function minimize(){
    maximize_btn.style.transform  = 'scale(1)';
    leftnav.style.left            = '-200px';
    topnav.style.left             = '0px';
    topnav.style.width            = '100%';
    main.style.left               = '0px';
    main.style.width              = '100%';

  }
  function maximize(){

    maximize_btn.style.transform  = 'scale(0)';
    leftnav.style.left            = '0px';
    topnav.style.left             = '200px';
    topnav.style.width            = '100%';
    main.style.left               = '200px';
    main.style.width              = '86%';

  }

  var home_act  = document.getElementById('home-action');
  var users_act  = document.getElementById('users-action');
  var rdv_act  = document.getElementById('rdv-action');
  var rec_act  = document.getElementById('rec-action');
  var cmp_act  = document.getElementById('account-action');

  var actions = [home_act,users_act,rdv_act,rec_act,cmp_act];
  function show(index){
    for (var i = 0; i < actions.length; i++) {
        if(i == index){
            actions[i].style.display  = 'block';
            buttons[i].style.color  = '#1E90FF';
            buttons[i].style.background  = '#ffc06f';
            buttons[i].style.borderLeft  = '3px solid #fff';

        }else{
          actions[i].style.display  = 'none';
          buttons[i].style.color  = 'black';
          buttons[i].style.background  = 'none';
          buttons[i].style.borderLeft  = 'none';

        }
    }
  }

  var home  = document.getElementById('home');
  var users  = document.getElementById('users');
  var rdv  = document.getElementById('RDV');
  var rec  = document.getElementById('reclamation');
  var cmp  = document.getElementById('account');

  var buttons = [home,users,rdv,rec,cmp];

  buttons[0].addEventListener('click',function(){
    show(0);
  },false);
  buttons[1].addEventListener('click',function(){
    show(1);
  },false);
  buttons[2].addEventListener('click',function(){
    show(2);
  },false);
  buttons[3].addEventListener('click',function(){
    show(3);
  },false);
  buttons[4].addEventListener('click',function(){
    show(4);
  },false);


  // Gerer le selectore des Medecin dans la gestion de rdv
  const selected = document.querySelector(".selected");
  const optionsContainer = document.querySelector(".options-container");


  selected.addEventListener("click", () => {
    optionsContainer.classList.toggle("active");
  });
