function jt(){}
function et(){}
function TK(){}
function DU(){}
function HU(){}
function YK(b,c){VK.call(this,b,c)}
function YQ(d){d.sort(function(b,c){return b-c})}
function EU(b,c){var d;d={};d.height=c;d.width=b;return d}
function IU(b,c,d){this.a=b;this.d=c;this.c=d;this.e=OY;this.b=OY}
function it(){var b;while(ft){b=ft;ft=ft.b;!ft&&(gt=null);ZS(b.a)}}
function ZS(b){var c;c=new FU(b.a.j,b.b);b.a.f.f.c>0&&DB(b.a.f);VG(b.a.f,c);cG(b.a.g,'Done.')}
function lt(){ht=new jt;zc((xc(),wc),1);!!$stats&&$stats(cd(NY,bV,-1,-1));ht.Ib();!!$stats&&$stats(cd(NY,CW,-1,-1))}
function FU(b,c){var d;this.a=new $J;d=new IU(this,b,c);SK(d,wb(Jl(ss,{10:1,13:1,14:1,15:1,16:1,17:1},1,[MW])));rC(this,this.a)}
function SQ(b,c){var d,e,f,g;e=0;d=b.length-1;while(e<=d){f=e+(d-e>>1);g=b[f];if(g<c){e=f+1}else if(g>c){d=f-1}else{return f}}return -e-1}
function sU(b,c){var d,e,f,g,i,j,k;d=new $wnd.google.visualization.DataTable;YQ(c);j={};j.fractionDigits=2;j.negativeColor='red';e=new $wnd.google.visualization.NumberFormat(j);for(f=0;f<b.length;++f){f==1&&d.addRows(b.length);for(i=0;i<b[f].length;++i){k=SQ(c,i);g=k>=0&k<c.length;if(f==0){if(g){d.addColumn((OK(),LK).a,b[0][i]);e.format(d,0)}else d.addColumn((OK(),MK).a,b[0][i])}else{b[f][i]!=null&&(g?(d.setValue(f-1,i,UM(b[f][i])),undefined):(d.setValue(f-1,i,b[f][i]),undefined))}}}return d}
var OY='35em',NY='runCallbacks1';_=jt.prototype=et.prototype=new V;_.gC=function kt(){return Cn};_.Ib=function ot(){it()};_.cM={};_=YK.prototype=TK.prototype=new UK;_.jc=function ZK(b){return new $wnd.google.visualization.Table(b)};_.gC=function $K(){return lq};_.cM={6:1,7:1,8:1,9:1,48:1,52:1};_=FU.prototype=DU.prototype=new qC;_.gC=function GU(){return Yr};_.cM={6:1,7:1,8:1,9:1,48:1,52:1};_.b=null;_=IU.prototype=HU.prototype=new V;_.gC=function JU(){return Xr};_.W=function KU(){this.a.b=new YK(sU(this.d,this.c),EU(this.e,this.b));ZJ(this.a.a,this.a.b)};_.cM={3:1};_.a=null;_.b=null;_.c=null;_.d=null;_.e=null;var Cn=yM(AY,'AsyncLoader1'),lq=yM(IY,'Table'),Yr=yM(MY,'VizTablePanel'),Xr=yM(MY,'VizTablePanel$3');RU(lt)();