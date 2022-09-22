# Izrada weba

## Tasks

- [ ] Move bg svg generation to scala backend. Generate svg on the backend and include it in the returned html. To avoid FOUC (flash of unstyled content).

## Quickstart

```
npm install

sbt run
```

## Scala.js

```bash
# development
sbt js/fastLinkJS

# production
sbt js/fullLinkJS
```

## Tailwind CSS

```bash
# watch
npm run watch

# production
npm run prod
```