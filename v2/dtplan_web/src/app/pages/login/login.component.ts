import { HttpClientModule } from '@angular/common/http';
import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/api/auth.service';



@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, HttpClientModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {  
  //private toasty = inject(ToastyService);
  private router = inject(Router);
  private authService = inject(AuthService);
  private fb = inject(FormBuilder);

  loginForm: FormGroup = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    senha: ['', Validators.required]
  });

  submit() {
    if (this.loginForm.valid) {
      const { email, senha } = this.loginForm.value;
      this.authService.login(email, senha).subscribe({
        next: (tokenDto) => {
          console.log('Token recebido:', tokenDto);
          this.router.navigate(['/home']);
        },
        error: (error) => {
          console.error('Erro ao fazer login:', error);
          //this.toasty.error('Erro ao fazer login');
        }
      });
    } else {
      console.log('Formulário inválido');
      //this.toasty.error('Formulário inválido');
    }
  }

  criarUsuario() {
    this.router.navigate(['/cadastro'])
  }
}