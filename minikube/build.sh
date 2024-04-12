#! /bin/bash -eu

echo "PROJECT_DIR=$PROJECT_DIR"
echo '[*] Starting minikube'

minikube start
eval $(minikube docker-env)

echo "[*] Building docker image from $PROJECT_DIR/Dockerfile"

docker build $PROJECT_DIR/ -t webapp

echo '[*] Copying resources to minikube server'

scp -r -i $(minikube ssh-key) $PROJECT_DIR/SQL  docker@$(minikube ip):/home/docker/db-init
scp -r -i $(minikube ssh-key) $PROJECT_DIR/WebApp  docker@$(minikube ip):/home/docker/WebApp

echo '[*] Creating volumes'

KUBE_DIR=$PROJECT_DIR/minikube

minikube kubectl -- apply -f $KUBE_DIR/pv-volume.yaml 
minikube kubectl -- apply -f $KUBE_DIR/pvc-volume.yaml 

echo '[*] Starting postgres'

minikube kubectl -- apply -f $KUBE_DIR/postgres.yaml

# Wait for ContainerCreating
sleep 45

c=0

# Waiting for ~3 minuts
while [ $c -lt 90 ]
do
    tmp=$(minikube kubectl -- logs -l app=postgres)
    if [[ $tmp == *'database system is ready to accept connections'* ]]; then
        echo '[*] Postgres started successfully'
        break
    fi
    sleep 2
    c=$((c+1))
done

if [[ $c == 90 ]]; then
    echo 'Timeout: Cannot start postrgres'
    exit 1
fi

echo '[*] Starting WebApp'

minikube kubectl -- apply -f $KUBE_DIR/app.yaml

# Wait for ContainerCreating
sleep 20

c=0

# Waiting for ~5 minuts
while [ $c -lt 100 ]
do
    tmp=$(minikube kubectl -- logs -l app=spring-app)
    if [[ $tmp == *'Started WebApplication in'* ]]; then
        echo '[*] WebApplication started successfully'
        break
    fi
    sleep 3
    c=$((c+1))
done

if [[ $c == 100 ]]; then
    echo 'Timeout: Cannot start WebApplication'
    exit 1
fi

link=$(minikube service spring-app --url)
echo "You can connect to WebApp using link: $link"
