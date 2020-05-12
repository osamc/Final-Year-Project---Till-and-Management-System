import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { JWTUserAPIService, PasswordRequest } from '../openapi';
import { ToasterService, ToastType } from '../toaster/toaster.service';

@Component({
  selector: 'app-jwtuser',
  templateUrl: './jwtuser.component.html',
  styleUrls: ['./jwtuser.component.css']
})
export class JwtuserComponent implements OnInit {

  accForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
    private jwtService: JWTUserAPIService,
    private toaster: ToasterService) {
    this.accForm = this.formBuilder.group({
      password: ['', Validators.required],
      newPass: ['', [Validators.required]],
      confPass: ['', Validators.required]
    }, {validators: this.matchingFields('newPass', 'confPass')});

    console.log(this.accForm);

  }

  ngOnInit(): void {
    
  }

  //We want to ensure that the two fields are identical
  matchingFields(field1: string, field2: string) {
    return (formGroup: FormGroup) => {
      const control1 = formGroup.controls[field1];
      const control2 = formGroup.controls[field2];
  
      if (control1.errors && control2.errors) {
        return;
      }

      if (control1.value !== control2.value) {
        control2.setErrors({mustMatch: true});
      }
    }
  }

  update() {
    if (this.accForm.valid) {
      let passReq: PasswordRequest = {currentPass: this.accForm.controls.password.value, newPass: this.accForm.controls.newPass.value};

      this.jwtService.updatePass(passReq).subscribe(res => {
        this.toaster.createToast("Account Updated Successfully.", ToastType.SUCCESS);
        this.accForm.reset();
      })

    } else {
      this.toaster.createToast("There are errors within the form.", ToastType.DANGER);
    }
  }

}
