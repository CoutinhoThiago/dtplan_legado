import { routes } from './../../../app.routes';
import { Component, inject, OnInit } from '@angular/core';
import { SidebarComponent } from '../../../shared/components/sidebar/sidebar.component';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { UsuarioGeneroMapperService } from '../../../shared/constants/usuario-genero--mapper.service';
import { UsuarioService } from '../../../core/services/api/usuario.service';
import { EditarUsuarioDTO } from '../../../core/models/usuario.dto';

@Component({
  selector: 'app-editar-perfil',
  standalone: true,
  imports: [SidebarComponent, ReactiveFormsModule, CommonModule],
  
  templateUrl: './editar-perfil.component.html',
  styleUrl: './editar-perfil.component.css'
})
export class EditarPerfilComponent implements OnInit {
  private router = inject(Router);
  private route = inject(ActivatedRoute)
  private fb = inject(FormBuilder)
  private usuarioService = inject(UsuarioService)
  public generoTipoMapper = inject(UsuarioGeneroMapperService)

  ususarioForm!: FormGroup;
  ususario: EditarUsuarioDTO = {
      email: '',
      senha: '',
      nome: '',
      cpf: '',
      genero: '',
      altura: 0,
      pesoAtual: 0,
      tipoUsuario: '',
      crn: '',
      cref: '',
      dataNascimento:''
    };
  
  generos: string[] = ['Masculino', 'Feminino', 'Outro'];
  usuarioId!: string;

  

  ngOnInit() {
    this.inicializarFormulario();
    this.carregarPerfil();
  }

  inicializarFormulario() {
    this.ususarioForm = this.fb.group({
      email: [''],
      senha: [''],
      nome: [''],
      cpf: [''],
      genero: [''],
      altura: [''],
      pesoAtual: [''],
      tipoUsuario: [''],
      crn: [''],
      cref: [''],
      dataNascimento: ['']
    });
  }

  carregarPerfil() {
    const token = localStorage.getItem('token');
    if (token) {
      this.usuarioService.consultarUsuario(token).subscribe({
        next: (ususario) => {
          this.ususario = ususario;
          const dataFormatada = this.formatarDataParaInput(ususario.dataNascimento);

          console.log(this.ususario.genero);
          this.ususarioForm.patchValue({
            email: this.ususario.email,
            senha: this.ususario.senha,
            nome: this.ususario.nome,
            cpf: this.ususario.cpf,
            //genero: this.ususario.genero,
            genero: this.generoTipoMapper.getGenero(this.ususario.genero),
            altura: this.ususario.altura,
            pesoAtual: this.ususario.pesoAtual,
            tipoUsuario: this.ususario.tipoUsuario,
            crn: this.ususario.crn,
            cref: this.ususario.cref,
            dataNascimento: dataFormatada
          });
        },
        error: () => {
          console.error('Erro ao carregar treino:');
        }
      });
    }
  }


  formatarDataParaInput(data: string | null): string {
    if (!data) return '';
  
    const dataObj = new Date(data);
    const ano = dataObj.getFullYear();
    const mes = String(dataObj.getMonth() + 1).padStart(2, '0'); // Ajusta para 2 dígitos
    const dia = String(dataObj.getDate()).padStart(2, '0'); // Ajusta para 2 dígitos
  
    return `${ano}-${mes}-${dia}`;
  }

  
  editar() {
    if (this.ususarioForm.valid) {
      const formData: EditarUsuarioDTO = this.ususarioForm.value;
      formData.dataNascimento = this.formatarDataParaEnvio(formData.dataNascimento);
  
      this.usuarioService.atualizarUsuario(formData).subscribe({
        next: (response) => {
          console.log('Perfil editado com sucesso!', response);
          this.voltarParaPerfil();
        },
        error: (error) => {
          console.error('Erro ao editar perfil', error);
        }
      });
    } else {
      console.error('Formulário inválido! Verifique os campos.');
    }
  }
  
  formatarDataParaEnvio(data: string | null): string {
    if (!data) return ''; // Retorna string vazia ao invés de null
    return new Date(data).toISOString(); // Converte para formato ISO
  }

  voltarParaPerfil() {
    console.log('Voltando para o perfil...');
    this.router.navigate(['/perfil']);
  }
}