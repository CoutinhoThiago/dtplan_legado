import { Component } from '@angular/core';
import { HeaderComponent } from '../../../shared/components/header/header.component';
import { EditarFichaDTO } from '../../../core/models/ficha.dto';

@Component({
  selector: 'app-editar-ficha',
  imports: [
    HeaderComponent
  ],
  standalone: true,
  templateUrl: './editar-ficha.component.html',
  styleUrl: './editar-ficha.component.css'
})
export class EditarFichaComponent {

  ficha: EditarFichaDTO = {
      nome: '',
      treinoId: 0,
      fichaExercicios: [],
    };

}
