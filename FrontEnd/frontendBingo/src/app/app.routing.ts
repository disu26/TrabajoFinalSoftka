import { ModuleWithProviders} from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LobbyComponent } from './components/lobby/lobby.component';
import { GameComponent } from './components/game/game.component';
import { LoginComponent } from './components/login/login.component';

const appRoutes: Routes = [
  {path:'lobby',component:LobbyComponent   },
  {path:'game',component:GameComponent   },
  {path:'',component:LoginComponent   },


];

export const appRoutingProviders : any[]=[];
export const routing : ModuleWithProviders<any> = RouterModule.forRoot(appRoutes);
export class AppRoutingModule { }
