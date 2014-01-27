var DOFUS_VER = '1.29'; //version dofus

API.execute("hello");

if(DOFUS_VER != '1.29'){
    API.execute('dofus-' + DOFUS_VER + '/player');
}
