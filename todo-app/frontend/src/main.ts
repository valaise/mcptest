import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { AppModule } from './app/app.module';
import { initTracing } from './app/tracing/tracing';

// Initialize OpenTelemetry tracing
initTracing();

platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err)); 