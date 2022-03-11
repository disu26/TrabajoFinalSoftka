import { ModuleWithProviders} from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LobbyComponent } from './components/lobby/lobby.component';

const appRoutes: Routes = [
  {path:'lobby',component:LobbyComponent   },

];

export const appRoutingProviders : any[]=[];
export const routing : ModuleWithProviders<any> = RouterModule.forRoot(appRoutes);
export class AppRoutingModule { }
