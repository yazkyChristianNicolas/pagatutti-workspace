import { ElementRef, Input } from '@angular/core';
import { HostListener } from '@angular/core';
import { Directive } from '@angular/core';

@Directive({
  selector: '[focusNextInput]'
})
export class FocusNextInputDirective {

@Input('focusNextInput') nextInput: string;

constructor(private _el: ElementRef) { }

@HostListener('keyup', ['$event']) onKeyDown(e: any) {
    if (e.srcElement.maxLength === e.srcElement.value.length) {

        e.preventDefault();

        let nextControl: any = document.getElementById(this.nextInput);
       // Searching for next similar control to set it focus
        while (true)
        {
            if (nextControl)
            {
                if (nextControl.type === e.srcElement.type)
                {
                    nextControl.focus();
                    return;
                }
                else
                {
                    nextControl = nextControl.nextElementSibling;
                }
            }
            else
            {
                return;
            }
        }
    }
}

}
