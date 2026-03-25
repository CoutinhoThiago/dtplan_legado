import { HttpClientModule } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/api/auth.service';
import { UsuarioService } from '../../core/services/api/usuario.service';

@Component({
  selector: 'app-cadastro',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, HttpClientModule],
  templateUrl: './cadastro.component.html',
  styleUrl: './cadastro.component.css'
})
export class CadastroComponent {
  private router = inject(Router);
  private authService = inject(AuthService);
  private fb = inject(FormBuilder);
  private usuarioService = inject(UsuarioService);

  cadastroForm: FormGroup;

  constructor() {
    this.cadastroForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      senha: ['', Validators.required],
      nome: ['', Validators.required],
      cpf: ['', Validators.required],
      isTreinador: [false], // Toggle para Treinador
      isNutricionista: [false], // Toggle para Nutricionista
      crn: [''], // Campo CRN (obrigatório se Nutricionista estiver ativado)
      cref: [''] // Campo CREF (obrigatório se Treinador estiver ativado)
    });

    // Adiciona validações dinâmicas para CRN e CREF
    this.cadastroForm.get('isTreinador')?.valueChanges.subscribe((value) => {
      this.updateCrefValidators(value);
    });

    this.cadastroForm.get('isNutricionista')?.valueChanges.subscribe((value) => {
      this.updateCrnValidators(value);
    });
  }

  // Atualiza validações do campo CREF
  updateCrefValidators(isTreinador: boolean) {
    const crefControl = this.cadastroForm.get('cref');
    if (isTreinador) {
      crefControl?.setValidators([Validators.required]);
    } else {
      crefControl?.clearValidators();
    }
    crefControl?.updateValueAndValidity();
  }

  // Atualiza validações do campo CRN
  updateCrnValidators(isNutricionista: boolean) {
    const crnControl = this.cadastroForm.get('crn');
    if (isNutricionista) {
      crnControl?.setValidators([Validators.required]);
    } else {
      crnControl?.clearValidators();
    }
    crnControl?.updateValueAndValidity();
  }

  // Submissão do formulário
  submit() {
    if (this.cadastroForm.valid) {
      const { email, senha, nome, cpf, isTreinador, isNutricionista, crn, cref } = this.cadastroForm.value;
      const tipoUsuario = this.getTipoUsuario(isTreinador, isNutricionista);

      this.usuarioService.cadastro(email, senha, nome, cpf, tipoUsuario, crn, cref).subscribe({
        next: () => {
          this.efetuarLogin(email, senha);
        },
        error: (error) => {
          console.error('Erro ao fazer cadastro:', error);
        }
      });
    } else {
      console.log('Formulário inválido');
    }
  }

  // Determina o tipo de usuário com base nos toggles
  getTipoUsuario(isTreinador: boolean, isNutricionista: boolean): string {
    if (isTreinador && isNutricionista) {
      return 'ambos';
    } else if (isTreinador) {
      return 'treinador';
    } else if (isNutricionista) {
      return 'nutricionista';
    } else {
      return 'normal';
    }
  }

  // Efetua login após o cadastro
  efetuarLogin(email: string, senha: string) {
    this.authService.login(email, senha).subscribe({
      next: (tokenDto) => {
        console.log('Token recebido:', tokenDto);
        this.router.navigate(['/home']);
      }
    });
  }

  // Navega para a página de login
  login() {
    this.router.navigate(['/login']);
  }
}